import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;


public class Club {
    static JTextArea textArea=new JTextArea(15,25);
    private DanceSquare danceSquare=new DanceSquare();
    public static void main(String [] args){
        Club club=new Club();
        club.open();
    }
    private void open()
    {
        makeGui();
        danceSquare.play();
    }
    private void makeGui()
    {
        JFrame frame=new JFrame("Клуб");
        JPanel buttonPanel=new JPanel();
        JLabel label=new JLabel("Вывод");
        JScrollPane scrollPane=new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JButton addDancerButton=new JButton("Добавить нового танцора");
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> System.exit(0));
        addDancerButton.addActionListener(new newDancerListener());
        JButton removeDancerButton = new JButton("Удалить танцора");
        removeDancerButton.addActionListener(new removeDancerListener());
        buttonPanel.add(addDancerButton);
        buttonPanel.add(removeDancerButton);
        buttonPanel.add(closeButton);
        JPanel labelPanel=new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(label);
        frame.add(BorderLayout.NORTH,labelPanel);
        frame.add(BorderLayout.CENTER,scrollPane);
        frame.add(BorderLayout.SOUTH,buttonPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,700 );
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private class newDancerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<MusicType> helpList=new DefaultListModel<>();
            ArrayList<MusicType> secondHelpList=new ArrayList<>();
            JFrame frame=new JFrame("Новый танцор");
            JPanel panelName=new JPanel();
            JPanel panelTypeLists=new JPanel();
            panelTypeLists.setLayout(new GridBagLayout());
            GridBagConstraints constraints=new GridBagConstraints();
            constraints.fill=GridBagConstraints.HORIZONTAL;
            constraints.weightx=0.5;
            constraints.gridy=0;
            JPanel buttonPanel=new JPanel(); //Нижняя панель с кнопками
            JLabel nameLabel=new JLabel("Имя");
            JLabel typeLabel=new JLabel("Выберите стили танца");
            JLabel selectedTypeLabel=new JLabel("Выберанные стили танца");
            JTextField jTextField=new JTextField(20);
            JList<MusicType> jList=new JList<>(MusicType.values()); //Список всех возможных типов танца
            JList<MusicType> dancerStyleList=new JList<>(helpList); //Выбрынные типы
            JScrollPane scrollPanejList=new JScrollPane(jList);
            JScrollPane scrollPaneDancerStyleList =new JScrollPane(dancerStyleList);
            scrollPanejList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPaneDancerStyleList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            JButton addButtton=new JButton("Добавить танцора");
            JButton addStyleButton = new JButton("Добавить стиль танца");
            addStyleButton.addActionListener(e13 -> {
                if(jList.getSelectedValue()!=null & !secondHelpList.contains(jList.getSelectedValue())) {
                    helpList.addElement(jList.getSelectedValue());
                    secondHelpList.add(jList.getSelectedValue());
                }
            });
            JButton removeStyleButton=new JButton("Удалить стиль танца");
            removeStyleButton.addActionListener(e14 -> {
                secondHelpList.remove(dancerStyleList.getSelectedValue());
                helpList.removeElement(dancerStyleList.getSelectedValue());
            });
            addButtton.addActionListener(e1 -> {
                if((secondHelpList.isEmpty())||(jTextField.getText().isEmpty()))
                {
                    JOptionPane.showMessageDialog(frame,"Не заполнено имя или стиль танца!");
                    return;
                }
                for (Observer dancer : danceSquare.getDancers()) {
                    Dancer d=(Dancer)dancer;
                    if(d.getName().equals(jTextField.getText()))
                    {
                        JOptionPane.showMessageDialog(frame,"Танцор с таким именем уже существует");
                        return;
                    }
                }
                danceSquare.addObserver(new Dancer(jTextField.getText(), secondHelpList));
                JOptionPane.showMessageDialog(frame,"Танцор добавлен");
                Club.textArea.append("Новый танцор по имени " + jTextField.getText()+"\n");
                jTextField.setText("");
                helpList.clear();
            });
            JButton closeButton=new JButton("Закрыть");
            closeButton.addActionListener(e12 -> frame.setVisible(false));
            panelName.add(nameLabel);
            panelName.add(jTextField);

            constraints.gridx=0;
            constraints.gridy=0;
            panelTypeLists.add(typeLabel,constraints);
            constraints.gridx=1;
            constraints.gridy=0;
            panelTypeLists.add(new Label(""),constraints);
            constraints.gridx=2;
            constraints.gridy=0;
            panelTypeLists.add(selectedTypeLabel,constraints);
            constraints.gridx=0;
            constraints.gridy=1;
            constraints.ipady=100;
            panelTypeLists.add(scrollPanejList,constraints);
            constraints.gridx=1;
            constraints.gridy=1;
            panelTypeLists.add(new Label(""),constraints);
            constraints.gridx=2;
            constraints.gridy=1;
            panelTypeLists.add(scrollPaneDancerStyleList,constraints);

            buttonPanel.add(addButtton);
            buttonPanel.add(closeButton);
            buttonPanel.add(addStyleButton);
            buttonPanel.add(removeStyleButton);
            frame.add(BorderLayout.NORTH,panelName);
            frame.add(BorderLayout.CENTER,panelTypeLists);
            frame.add(BorderLayout.SOUTH,buttonPanel);
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.setSize(600,300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
    private class removeDancerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<Observer> helpList=new DefaultListModel<>();
            Iterator iterator=danceSquare.getDancers().iterator();
            for (Observer o : danceSquare.getDancers()) {
                helpList.addElement(o);
            }
            JFrame frame=new JFrame("Удаление танцора");
            JPanel buttonPanel=new JPanel();
            JButton closeButton = new JButton("Закрыть");
            JList<Observer> dancersList=new JList<>(helpList);
            JScrollPane jScrollPane=new JScrollPane(dancersList);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                }
            });
            JButton removeDancer = new JButton("Удалить танцора");
            removeDancer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Observer dancer1 : danceSquare.getDancers()) {
                    Dancer dancer = (Dancer) dancer1;
                    if (( dancersList.getSelectedValue().toString()).equals(dancer.getName())) {
                        danceSquare.removeObserver(dancer);
                        helpList.removeElement(dancer);
                        Club.textArea.append(dancer.getName()+ " покинул заведение\n");
                        break;
                    }
                }
                }
            });
            buttonPanel.add(removeDancer);
            buttonPanel.add(closeButton);
            frame.add(BorderLayout.CENTER,jScrollPane);
            frame.add(BorderLayout.SOUTH,buttonPanel);
            frame.setSize(450,200);
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        }
    }
}
