import java.util.ArrayList;

public class DanceSquare implements Observable{
    private ArrayList<Observer> dancers=new ArrayList<>();
    private MusicType musicType;

    private void setMusicType(MusicType musicType) {

        this.musicType = musicType;
        notifyObserver();
    }

    ArrayList<Observer> getDancers() {
        return dancers;
    }

    @Override
    public void addObserver(Observer observer) {
        dancers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        dancers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        dancers.forEach(observer -> observer.handleEvent(this.musicType));
    }


    void play() {
        MusicType [] arrMT= MusicType.values();
        while (true)
        {
            for (MusicType anArrMT : arrMT) {
                Club.textArea.append("-----------------------------------------------\n");
                Club.textArea.append("DJ изменил музыку на " + anArrMT + "\n" +
                        "-----------------------------------------------\n");
                this.setMusicType(anArrMT);
                if (dancers.isEmpty()) {
                    Club.textArea.append("Добавьте нового танцора\n");
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
