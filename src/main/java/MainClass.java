import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.lang.reflect.Field;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Run Main Class");
        //받아서 dispose 할 것인지 알려줄 수 있음.
//        Disposable disposable =  Observable.just("Observable")
//                                            .subscribeOn(Schedulers.newThread()) //스레드가 동작하기 전 dipose돼서 Observable이 출력되지 않음.
//                                            .subscribe(s -> print(s)); //결과값을 return.
//        System.out.println("end");
//        try{
//            Thread.sleep(1*1000); //1초
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//        disposable.dispose();

        //Create Obserbable
        Observable<Integer> observable =
                Observable.create(emitter -> {
                    //1~10까지 배출 후 완료하겠다
                    for(int i = 0; i < 10 ; i++){
                        emitter.onNext(i);
                        if(i >=9){
                            throw new Exception(i + " is greater than 9");
                        }
                    }
                    emitter.onComplete();
                });

        Disposable db = observable
                        .onErrorReturn(new Function<Throwable, Integer>() {
                            @Override
                            public Integer apply(Throwable throwable) throws Exception {
                                return -1;
                            }
                        })
                        .map(new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(Integer data) throws Exception {
                                return data < 0 ? 100 : data;
                            }
                        })
                        .subscribe(s -> System.out.println(s),
                                err -> System.out.println("err:"+ err),
                                 () -> System.out.println("Complete"));
        try{
            Thread.sleep(1*1000); //1초
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        db.dispose();


        System.out.println("Run Main Class End");
    }

//    private static void print(T data) {
//        System.out.println(data);
//    }

}
