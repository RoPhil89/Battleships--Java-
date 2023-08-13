//This is a Battleship simulator on a 10x10 field. 
//Each ship has a size of 3 fields.
//All code handwritten and original.
//Features a simple Algorithm that tries to sink the full ship (smartmode)
//For method declaration see bottom


import java.util.Random;
import java.util.Scanner;

public class Battleships {
    public static void main( String args[] ) {

        Random rand = new Random();
        Scanner sc = new Scanner(System.in);

                //Setting up the playing fields as 2D arrays and filling it with zeroes.
                int positionsComp[][] = new int[10][10];
                fillField(positionsComp);
                
                int positionsPlayer[][] = new int[10][10];
                fillField(positionsPlayer);
                
                //Setting up two additional identical fields to keep track of positions that have been fired at.
                int keepPlayerNumber[][] = new int[10][10];
                fillField(keepPlayerNumber);

                int keepComputerNumber[][] = new int[10][10];
                fillField(keepComputerNumber);
                
                //Computer places its 5 ships:
                for (int x = 0; x < 5; x++) {
                    int shipx = rand.nextInt(1,8); 
                    int shipy = rand.nextInt(1,8);
                    while (positionsComp[shipx][shipy] == 1) {
                        shipx = rand.nextInt(1,8);
                        shipy = rand.nextInt(1,8);
                    }
                    int coin = rand.nextInt(0,1);   //A coin flip decides if ship is horizontal or vertical.
                    if (coin == 0) {
                        positionsComp[shipx][shipy] = 1;
                        positionsComp[shipx - 1][shipy] = 1;
                        positionsComp[shipx + 1][shipy] = 1;
                    } else {
                        positionsComp[shipx][shipy] = 1;
                        positionsComp[shipx][shipy - 1] = 1;
                        positionsComp[shipx][shipy + 1] = 1;
                    }
    
    
                }

                //Player places their 5 ships:
                for (int x = 0; x < 5; x++) {
                    try {
                        System.out.println("Where do you want to position your " + (x + 1) + ". ship (X/Y)?");
                        int posx = sc.nextInt();    //x coordinate
                        int posy = sc.nextInt();    //y coordinate
                        while (positionsPlayer[posx][posy] == 1) {      //prevents user from placing ship over another ship
                            System.out.println("You already have a ship here, try again");
                            posx = sc.nextInt();
                            posy = sc.nextInt();
                        }
                        int coin = rand.nextInt(0,1); //for ease of use, coin flip decides if ship is horizontal or vertical
                        if (coin == 0) {
                            positionsPlayer[posx][posy] = 1;
                            positionsPlayer[posx - 1][posy] = 1;
                            positionsPlayer[posx + 1][posy] = 1;
                        } else {
                            positionsPlayer[posx][posy] = 1;
                            positionsPlayer[posx][posy - 1] = 1;
                            positionsPlayer[posx][posy + 1] = 1;
                        }
                    } catch(Exception e) {
                        System.out.println("Please input x-coordinate (1-8) and y-coordinatae (1-8).");
                        System.out.println("Where do you want to position your " + (x + 1) + ". ship (X/Y)?");
                        int posx = sc.nextInt();    //x coordinate
                        int posy = sc.nextInt();    //y coordinate
                        while (positionsPlayer[posx][posy] == 1) {      //prevents user from placing ship over another ship
                            System.out.println("You already have a ship here, try again");
                            posx = sc.nextInt();
                            posy = sc.nextInt();
                        }
                        int coin = rand.nextInt(0,1);
                        if (coin == 0) {
                            positionsPlayer[posx][posy] = 1;
                            positionsPlayer[posx - 1][posy] = 1;
                            positionsPlayer[posx + 1][posy] = 1;
                        } else {
                            positionsPlayer[posx][posy] = 1;
                            positionsPlayer[posx][posy - 1] = 1;
                            positionsPlayer[posx][posy + 1] = 1;
                        }

                    }
                }

                //Global variables:
                int tries = 0;
                boolean smartMode = false;
                int lastHitx = 0;
                int lastHity = 0;
                int hitcount = 0;
                boolean cont = true;
                int lockOnx = 0;
                int lockOny = 0;
                
                
                //Main game loop
                
                while (cont == true) {

                    //Player round:
                    tries = tries + 1;
                    System.out.println("Choose a position to shoot a missile from 0-9");
                    int askx = sc.nextInt();
                    int asky = sc.nextInt();
                    while (keepPlayerNumber[askx][asky] == 1) {     //prevents player from firing at the same spot twice
                        System.out.println("You already fired this spot! Try again.");
                        System.out.println("Choose a position to shoot a missile from 0-9");
                        askx = sc.nextInt();
                        asky = sc.nextInt();
                    }
                    if (positionsComp[askx][asky] == 1) {
                        System.out.println("Hit!");
                        positionsComp[askx][asky] = 0;
                    }
                    else {
                        System.out.println("Miss");
                    }
                    keepPlayerNumber[askx][asky] = 1;


                
                    if (checkWinner(positionsComp) == 50) {     //if computer field contains 50 zeroes, player wins
                        cont = false;
                        System.out.println("You won!");
                        System.out.println("You got it in " + tries + " tries");
                        break;
                    }
                    //Computer
                    if (smartMode == true){     //Smartmode lets computer try to sink the full ship.
                        int coin = rand.nextInt(0,3);
                        try {
                            if (coin == 0) {
                                lockOnx = lastHitx - 1;
                                lockOny = lastHity;
                            } else if (coin == 1) {
                                lockOnx = lastHitx + 1;
                                lockOny = lastHity;
                            } else if (coin == 2) {
                                lockOnx = lastHitx;
                                lockOny = lastHity - 1;
                            } else {
                                lockOnx = lastHitx;
                                lockOny = lastHity + 1;
                            }
                            while (keepComputerNumber[lockOnx][lockOny] == 1) {     //prevents computer from firing at the same spot twice
                                coin = rand.nextInt(0, 3);
                                if (coin == 0) {
                                    lockOnx = lastHitx - 1;
                                    lockOny = lastHity;
                                } else if (coin == 1) {
                                    lockOnx = lastHitx + 1;
                                    lockOny = lastHity;
                                } else if (coin == 2) {
                                    lockOnx = lastHitx;
                                    lockOny = lastHity - 1;
                                } else {
                                    lockOnx = lastHitx;
                                    lockOny = lastHity + 1;
                                }
                            }
                        }
                        catch (Exception e) {       //if smartmode fails, back to normal mode
                            smartMode = false;
                            lockOnx = rand.nextInt(0, 9);
                            lockOny = rand.nextInt(0, 9);
                            while (keepComputerNumber[lockOnx][lockOny] == 1) {
                                lockOnx = rand.nextInt(0, 9);
                                lockOny = rand.nextInt(0, 9);
                            }
                        }
                    } else { //normal mode
                        lockOnx = rand.nextInt(0, 9);
                        lockOny = rand.nextInt(0, 9);
                        while (keepComputerNumber[lockOnx][lockOny] == 1) {
                            lockOnx = rand.nextInt(0, 9);
                            lockOny = rand.nextInt(0, 9);
                        }
                    }
                    System.out.println("The computer fires at " + lockOnx + " " + lockOny);
                    if (positionsPlayer[lockOnx][lockOny] == 1) {
                        System.out.println("Hit!");
                        smartMode = true;       //smart mode gets activated once computer hits a ship
                        lastHitx = lockOnx;
                        lastHity = lockOny;
                        hitcount = hitcount + 1;    //smart mode gets deactivated if computer hits 3 times (full ship sunk)
                        positionsPlayer[lockOnx][lockOny] = 0;
                    }
                    else {
                        System.out.println("The computer missed");
                        if (hitcount == 3) {
                            smartMode = false;
                            hitcount = 0;
                        }
                    }
                    keepComputerNumber[lockOnx][lockOny] = 1;
                    

                    if (checkWinner(positionsPlayer) == 50) {   //if player field contains only 0, computer wins
                        cont = false;
                        System.out.println("Game over. Computer won");
                    }
                }
    
    
            }

            //Methods:

            static void fillField(int array[][]) {  //fills the 2D array with zeroes
                for (int x = 0; x < 10; x++) {
                    for (int y = 0;y < 10;y++) {
                        array[x][y] = 0;
                    }
                }
              }

            static int checkWinner(int array[][]) { //counts the zeroes in a playing field
                int counter = 0;
                for (int x = 0; x < 10;x++) {
                    for (int y = 0; y < 10; y++) {
                        if (array[x][y] == 0) {
                            counter = counter + 1;
                        }
                    }
                }
                return counter;

            }
            




        }
