import java.util.concurrent.Semaphore;

public class MontanhaRussa {
    public static int Npass = 0;
    public static int passageiros;
    public static long tempoPasseio;
    public static Semaphore carrinho = new Semaphore(1, true);
    public static Semaphore andando = new Semaphore(1, true);
    public static Semaphore mutex = new Semaphore(1, true);
    public static Semaphore passageiro = new Semaphore(1, true);


    public static void main(String[] args) throws InterruptedException {
        passageiros = 2;
        int pessoas = 6;
        int tempoEntrada = 5;
        int tempoSaida = 5;
        tempoPasseio = 5;
        passageiro.release(passageiros - 1);
        new Vagao();
        for (int i = 0; i < pessoas; i++) {
            new Passageiro("Pessoa " + (i + 1), tempoEntrada, tempoSaida);
        }
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
                System.out.println("Passageiro :" + Thread.currentThread().getName());

                if (MontanhaRussa.Npass == MontanhaRussa.passageiros) {
                    System.out.println("Ultimo Passageiro :" + Thread.currentThread().getName());
                    MontanhaRussa.carrinho.release();
                    System.out.println("Carrinho Andando :" + Thread.currentThread().getName());
                    while (true) {
                        if (MontanhaRussa.andando.availablePermits() >= 1) {
                            break;
                        }
                    }
                    MontanhaRussa.andando.acquire();
                    MontanhaRussa.mutex.release();

                } else {
                    MontanhaRussa.mutex.release();
                    System.out.println("Passageiro Esperando:" + Thread.currentThread().getName());
                    while (true) {
                        if (MontanhaRussa.andando.availablePermits() >= 1) {
                            break;
                        }
                    }
                    MontanhaRussa.andando.acquire();
                    System.out.println("Passageiro depois de Passear:" + Thread.currentThread().getName());
                }
                Dembarque(tempoDeDesembarque);
                MontanhaRussa.mutex.acquire();
                MontanhaRussa.Npass--;
                if (MontanhaRussa.Npass == 0) {
                    System.out.println("Ultimo Passageiro desembarcou:" + Thread.currentThread().getName());
                    MontanhaRussa.passageiro.release(MontanhaRussa.passageiros);
                    MontanhaRussa.mutex.release();
                } else {
                    System.out.println("Passageiro desembarcou:" + Thread.currentThread().getName());
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