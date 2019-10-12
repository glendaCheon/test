import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Run Main Class start");
        // Create Observable
        Observable<Integer> observable =
                Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    for(int i = 0 ; i < 10; i++) {
                        if(i >= 9) {
                            throw new Exception("i is greater than 9");
                        } else {
                            emitter.onNext(i);
                        }                    }
                    emitter.onComplete();
                });

        Disposable db = observable
                .doOnNext(integer -> checkThreadName())
                .onErrorReturn((throwable -> -1))
                .map((data) -> data < 0 ? 100 : data)
                .observeOn(Schedulers.newThread())
                .map( data -> data * 2)
                .doOnNext(integer -> checkThreadName())
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer data) throws Exception {
                        return Observable.just(data * 2);
                    }
                })
                .doOnNext(integer -> checkThreadName())
                .subscribeOn(Schedulers.newThread())
                .subscribe(s -> System.out.println(s),
                        error -> System.out.println("Error : " + error),
                        () -> System.out.println("Complete"));

        System.out.println("Run Main Class end");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        db.dispose();
    }

    private static void checkThreadName() {
        System.out.println(Thread.currentThread().getName());
    }

//    private static void print(T data) {
//        System.out.println(data);
//    }
}