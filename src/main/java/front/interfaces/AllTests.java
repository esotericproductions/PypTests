package front.interfaces;

public interface AllTests<T extends AllTests<T>> {

    AllTests<T> startPypTest(Object data);
    AllTests<T> stopPypTest();

}
