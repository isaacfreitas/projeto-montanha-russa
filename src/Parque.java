/*
import montanhaRussa.Passageiro;
import montanhaRussa.Vagao;
*/

import java.util.concurrent.Semaphore;


public class Parque {
    public static void main(String[] args) throws InterruptedException {
        int pessoas = 6;
        Vagao thread = new Vagao(2, 10);
        Passageiro[] threads = new Passageiro[pessoas];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Passageiro("Pessoa " + (i + 1), 5);
        }

    }

}

//#####################################################################################
class Vagao implements Runnable {
    public static int vaga;
    public static long passeio;
    public static Semaphore mutex = new Semaphore(1, true);

    public Vagao(int vaga, long tempo) throws InterruptedException {
        Vagao.vaga = vaga;
        Thread thread = new Thread(this, "Vagao");
        thread.start();
        passeio = tempo;
        mutex.acquire();
    }

    public void Passear(long contado) {
        System.out.println("Percorrendo a montanha russa" + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        long fim = 0;
        while (fim == contado * 1000) {
            fim = System.currentTimeMillis() - start;
        }
        System.out.println("Fim do Passeio" + Thread.currentThread().getName());
        Passageiro.mutex.release(Vagao.vaga);
        Passageiro.observar.release(Vagao.vaga);
        Passageiro.fimDePasseio = true;

    }

    @Override
    public void run() {
        do {
            try {
                System.out.println("tentou passear " + Thread.currentThread().getName());
                mutex.acquire();
                System.out.println("conseguiu passear " + Thread.currentThread().getName());
                Passear(passeio);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }

}

//#####################################################################################
class Passageiro implements Runnable {

    public static final Semaphore mutex = new Semaphore(Vagao.vaga, true);
    public static Semaphore observar = new Semaphore(Vagao.vaga, true);
    public static Semaphore desembarcar = new Semaphore(Vagao.vaga, true);
    //public static Semaphore embarque = new Semaphore(Vagao.vaga, true);
    public static boolean fimDePasseio = false;
    public String nome;
    public long oDeTempoDeEmbarque;


    public Passageiro(String nome, long oDeTempoDeEmbarque) {
        this.nome = nome;
        this.oDeTempoDeEmbarque = oDeTempoDeEmbarque;
        Thread thread = new Thread(this, nome);
        thread.start();
    }

    public void Observando() {
        System.out.println("Observando :" + Thread.currentThread().getName());
        Vagao.mutex.release();
        while (true) {
            if (fimDePasseio) {
                break;
            }
        }
        System.out.println("#####Observando Tempo###### FIM " + Thread.currentThread().getName());
        Dembarcar();
    }

    public void ContadorDeTempo(long contado) {
        long start = System.currentTimeMillis();
        long fim = 0;
        do {
            fim = System.currentTimeMillis() - start;
        } while (fim < contado * 1000);
    }

    public void Embarcar() throws InterruptedException {
        System.out.println("Embarcou " + Thread.currentThread().getName() + " No lugar ");
        System.out.println("Tempo De Embarque :" + Thread.currentThread().getName());
        ContadorDeTempo(oDeTempoDeEmbarque);
        observar.acquire();
        Observando();
    }

    public void Dembarcar() {
        System.out.println("Tempo De Desembarque :" + Thread.currentThread().getName());
        ContadorDeTempo(oDeTempoDeEmbarque);
        System.out.println("Desembarcou " + Thread.currentThread().getName());
        desembarcar.release();
    }


    @Override
    public void run() {
        do {
            try {
                System.out.println("            Tentou " + Thread.currentThread().getName());
                mutex.acquire();
                desembarcar.acquire();
                Passageiro.fimDePasseio = false;
                System.out.println("Conseguiu " + Thread.currentThread().getName());
                Embarcar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }


}