import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
//this is going to store all the pipes in our game 
import java.util.Random;
//using to place pipes in randowm position
import javax.swing.*;
public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    // we are having flappy bird this class inherit J panel class this is going to find 
    int boardwidth = 360;
    int boardHeight = 640;

    //changing background;
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //adding bird
    int birdX = boardwidth /8;
    int birdY = boardHeight /2;
    //int bird x the position will be 1/8th of the board
    //for birdy the position will be 1/2 of the board
    int birdWidth = 34;
    int birdHeight = 24;
     //creatinga class to hold this values
    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
        Bird(Image img){
            this.img = img;
        }
    }
    //pipes
    int pipeX = boardwidth;
    int pipeY = 0;
    int pipeWidth = 64;//scale by 1/6
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        Pipe(Image img){
            this.img = img;
        }
    }

    //gaem logic;
    Bird bird;
    
    int velocityX=-4;//moves pipe to the left speed(simulates moving the bird to right)
    int velocityY=0;
    int gravity=1;

    ArrayList<Pipe> pipes;
    Random random=new Random();//for pipes position

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver=false;
    double score=0;


    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardHeight));
        //setBackground(Color.blue);
         //load images;
         setFocusable(true);
         //it's going to make sure flappy bird class j panel takes in key events
         addKeyListener(this);
         //checking 3 function pressed,typed ,released
         backgroundImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
         birdImg= new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
         topPipeImg= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
         bottomPipeImg= new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        
         bird= new Bird(birdImg);//bird;

         pipes=new ArrayList<Pipe>();
         
         //game timer
        
         //place timer
         placePipeTimer=new Timer(1500,new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e){

            placePipes();
         }
         });
        placePipeTimer.start();
        
        
        
         gameLoop=new Timer(1000/60, this);
         gameLoop.start();
    
    
        }


        //call a function for pipes
        void placePipes(){

            //mobing each pipe upwards  by a quarter of it's height
            //Math.random()*pipeHeight/2-
            //(0-1)*pipeHeight/2 ->(0-256)
        //128
        //and if one then subtrat pipHeight/2
        //0-128-(0-256)-> so the range is going to be from pipeHeight/4 ->3/4 pipeHeight
       
            int randomPipeY=(int)(pipeY-pipeHeight/4 - Math.random()*(pipeHeight/2));
          int openingSpace =boardHeight/4;
          
            Pipe   topPipe=new Pipe(topPipeImg);
          topPipe.y=randomPipeY;
            pipes.add(topPipe);
            Pipe bottomPipe= new Pipe(bottomPipeImg);
            //for shifting downwards
            bottomPipe.y=topPipe.y+pipeHeight+openingSpace;
            pipes.add(bottomPipe);

        }

     
        //define function
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
        }
        public void draw(Graphics g){
           
            //background
            //00 means top left corner
            g.drawImage(backgroundImg, 0 , 0,this.boardwidth,this.boardHeight,null );
           
            g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
            

            for(int i=0;i<pipes.size();i++){
                Pipe pipe=pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

            }
            //score
            g.setColor(Color.white);
            g.setFont(new Font("Cambria", Font.PLAIN, 32));
            if (gameOver) {
                g.drawString("Game Finished: " + String.valueOf((int) score), 10, 35);
            } else {
                g.drawString(String.valueOf((int) score), 10, 35);
            }
        
        
        }
        //move function
        public void move() {
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y, 0); // Apply gravity and limit to top of screen
    
            // Move pipes
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.x += velocityX;
    
                if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                    score += 0.5; // Add score for each pipe set passed
                    pipe.passed = true;
                }
    
                if (collision(bird, pipe)) {
                    gameOver = true;
                }
            }
        if (bird.y>boardHeight) {
            gameOver=true;
        }


        }
        boolean collision(Bird a, Pipe b) {
            return a.x < b.x + b.width &&   // a's top left doesn't reach b's top right
                   a.x + a.width > b.x &&   // a's top right passes b's top left
                   a.y < b.y + b.height &&  // a's top left doesn't reach b's bottom left
                   a.y + a.height > b.y;    // a's bottom left passes b's top left
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            //action performed
            move();
            repaint();
            if (gameOver) {
                placePipeTimer.stop();
                gameLoop.stop();
            }
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {
                velocityY=-9;
               
                if(gameOver){
                    bird.y = birdY;
                    velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
               }
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
        @Override
        public void keyReleased(KeyEvent e) {
            
        }
}