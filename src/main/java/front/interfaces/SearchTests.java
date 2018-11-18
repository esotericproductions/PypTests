package front.interfaces;

public interface SearchTests<T extends SearchTests<T>> {

    SearchTests<T> searchSingleWord();
    SearchTests<T> tapImage();
    SearchTests<T> openShareMenu();
    SearchTests<T> openPopup();
    SearchTests<T> closePopup();

    SearchTests<T> swipeUp();
    SearchTests<T> swipeDown();
}
