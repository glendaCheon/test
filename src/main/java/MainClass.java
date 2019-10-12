import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Run Main Class");


        //받아서 dispose 할 것인지 알려줄 수 있음.
        Disposable disposable =  Observable.just("Observable")
                                            .subscribeOn(Schedulers.newThread()) //스레드가 동작하기 전 dipose돼서 Observable이 출력되지 않음.
                                            .subscribe(s -> print(s)); //결과값을 return.
        System.out.println("end");
        try{
            Thread.sleep(1*1000); //1초
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        disposable.dispose();

    }

    private static void print(String data) {
        System.out.println(data);
    }

}
