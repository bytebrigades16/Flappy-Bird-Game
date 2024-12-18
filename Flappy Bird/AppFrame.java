import javax.swing.JFrame;

public class AppFrame extends JFrame {
    AppFrame(){
        int w=Integer.parseInt(ResourceBundleData.getBundleData("FRAME_WIDTH"));
        int h=Integer.parseInt(ResourceBundleData.getBundleData("FRAME_HEIGHT"));
        initApp(w,h);
    }

    public void initApp(int w,int h){

        setTitle(" Flappy bird ");
        setSize(w,h);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        AppPanel ap = new AppPanel();
        add(ap);
        setVisible(true);

    }
}