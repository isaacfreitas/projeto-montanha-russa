import java.util.concurrent.Semaphore;

public class MontanhaRussa {
    public static int Npass = 0;
    public static int passageiros;
    public static long tempoPasseio;
    public static Semaphore carrinho = new Semaphore(1, true);
    public static Semaphore andando = new Semaphore(1, true);
    public static Semaphore mutex = new Semaphore(1, true);
    public static Semaphore passageiro;

    public static void adicionarVagao(Wagon vagao) throws InterruptedException {
        passageiros = vagao.chairCount;
        tempoPasseio = (long) vagao.transitDuration;
        passageiro = new Semaphore(passageiros, true);
        new Vagao();
    }

    public static void adicionarPassageiro(Passenger passageiro) {
        new Passageiro(passageiro.toString(), (long) passageiro.boardingDuration, (long) passageiro.landingDuration);
    }
}

class Passageiro implements Runnable {
    public long tempoDeEmbarque;
    public long tempoDeDesembarque;

    public Passageiro(String nome, long tempoDeEmbarque, long tempoDeDesembarque) {
        this.tempoDeEmbarque = tempoDeEmbarque;
        this.tempoDeDesembarque = tempoDeDesembarque;
        Thread thread = new Thread(this, nome);
        thread.start();
    }

    private void Embarque(long contado) {
        long start = System.currentTimeMillis();
        long fim = 0;
        do {
            fim = System.currentTimeMillis() - start;
        } while (fim < contado * 1000);
    }

    private void Dembarque(long contado) {
        long start = System.currentTimeMillis();
        long fim = 0;
        do {
            fim = System.currentTimeMillis() - start;
        } while (fim < contado * 1000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                MontanhaRussa.passageiro.acquire();
                Embarque(tempoDeEmbarque);
                MontanhaRussa.mutex.acquire();
                MontanhaRussa.Npass++;
                Main.printToConsole("Passageiro :" + Thread.currentThread().getName());

                if (MontanhaRussa.Npass == MontanhaRussa.passageiros) {
                    Main.printToConsole("Ultimo Passageiro :" + Thread.currentThread().getName());
                    MontanhaRussa.carrinho.release();
                    Main.printToConsole("Carrinho Andando :" + Thread.currentThread().getName());
                    while (true) {
                        if (MontanhaRussa.andando.availablePermits() >= 1) {
                            break;
                        }
                    }
                    MontanhaRussa.andando.acquire();
                    MontanhaRussa.mutex.release();

                } else {
                    MontanhaRussa.mutex.release();
                    Main.printToConsole("Passageiro Esperando:" + Thread.currentThread().getName());
                    while (true) {
                        if (MontanhaRussa.andando.availablePermits() >= 1) {
                            break;
                        }
                    }
                    MontanhaRussa.andando.acquire();
                    Main.printToConsole("Passageiro depois de Passear:" + Thread.currentThread().getName());
                }
                Dembarque(tempoDeDesembarque);
                MontanhaRussa.mutex.acquire();
                MontanhaRussa.Npass--;
                if (MontanhaRussa.Npass == 0) {
                    Main.printToConsole("Ultimo Passageiro desembarcou:" + Thread.currentThread().getName());
                    MontanhaRussa.passageiro.release(MontanhaRussa.passageiros);
                    MontanhaRussa.mutex.release();
                } else {
                    Main.printToConsole("Passageiro desembarcou:" + Thread.currentThread().getName());
                    MontanhaRussa.mutex.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

class Vagao implements Runnable {
    public Vagao() throws InterruptedException {
        MontanhaRussa.carrinho.acquire();
        MontanhaRussa.andando.acquire();
        Thread thread = new Thread(this, "Vagao");
        thread.start();
    }

    private void Passear(long contado) {
        long start = System.currentTimeMillis();
        long fim = 0;
        do {
            fim = System.currentTimeMillis() - start;
        } while (fim < contado * 1000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                MontanhaRussa.carrinho.acquire();
                Passear(MontanhaRussa.tempoPasseio);
                MontanhaRussa.andando.release(MontanhaRussa.passageiros);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

}