package hr.ferit.antotufekovic.notecounter;

public interface NameClickInterface {
    void onDeleteClicked(int position);
    void onIncrementClicked(int position, Entry unit);
    void onDecrementClicked(int position, Entry unit);

}
