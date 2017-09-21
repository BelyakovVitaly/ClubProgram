import java.util.ArrayList;

public class Dancer implements Observer{
    private String name;
    private ArrayList<MusicType> musicType;
    private int status;

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    Dancer(String name, ArrayList<MusicType> musicType) {
        this.name=name;
        this.musicType =new ArrayList<>(musicType);
    }

    @Override
    public void handleEvent(MusicType  musicType) {
        if(this.musicType.contains(musicType))
        {
            switch (status) {
                case 1: {
                    Club.textArea.append("Танцор " + this.name + " все еще танцует!\n");
                    break;
                }
                default:{
                    status = 1;
                    Club.textArea.append("Танцор " + this.name + " вышел на танцпол!\n");
                }
            }
        }else
        {
            switch (status) {
                case 2: {
                    Club.textArea.append("Танцор " + this.name + " все еще в баре\n");
                    break;
                }
                default: {
                    status = 2;
                    Club.textArea.append("Танцор " + this.name + " пошел в бар\n");
                }
            }
        }
    }
}
