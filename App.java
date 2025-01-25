import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
       int boardwidth=360;
       int boardHeight=640;
       JFrame frame = new JFrame("Flappy Bird");
      
       frame.setSize(boardwidth,boardHeight);
       frame.setLocationRelativeTo(null);
       
       //place the window at the center of our screen and then it's also make sure the user can not resize the window
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   // now we have window so we need to add a j panel which is what we're going to be using for our canvas so with the j we can draw our game 
      //add a nwe file and calling it flappyh    
       
      //adding jpannel ont our frame 

//first need to create an instance of flappybird
    FlappyBird flappyBird=new FlappyBird();
//now let's add this to frame
    frame.add(flappyBird);
    frame.pack();
   flappyBird.requestFocus();
   frame.setVisible(true);
}
}
