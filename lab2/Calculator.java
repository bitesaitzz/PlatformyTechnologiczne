package org.example;

public class Calculator implements  Runnable{
    private Input input; private Output output; private int order; private int num;
    public Calculator(Input input, Output output, int order){
        this.input = input;
        this.output = output;
        this.order = order;

    }
    @Override
    public void run() {
        while (true){
            if(!Thread.currentThread().isInterrupted()){
                Pair value = new Pair();
                try {
                    value = input.take();
                } catch (InterruptedException e) {

                }
                try {

                    if(value.id==0){
                        break;
                    }

                    float sum = 0;
                    for(int n = 1; n <= value.element; n++ ){
                        sum += (Math.pow(-1, n-1)/(2*n-1));
                        num = n;
                        Thread.sleep(100);
                        //procent = (int)((Float.valueOf(n))/Float.valueOf(value.element)*100);

                    }
                    sum *= 4;

                    output.howManyDone++;
                    float procent = ((float)num/(float)value.element);
                    Integer procentUsage = (int)(procent*100) ;
                    String var = "Done ID: "+value.id+". Wartosc Pi dla N =" +value.element+" bedzie "+sum+" . -- Procent Wykonania = "+procentUsage+"%.";
                    System.out.println(var);
                    output.put(var);

                    continue;
                } catch (InterruptedException e) {
                    float procent = ((float)num/(float)value.element);

                    String var = "Not Done ID: "+value.id+". Wartosc Pi dla N =" +value.element+" bedzie ___ . -- Procent Wykonania = "+procent*100+"%.";
                    System.out.println(var);
                    output.put(var);
                }
            }
            return;
        }
    }
}