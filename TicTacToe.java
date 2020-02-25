// Java code to implement a graphical user interface (GUI) for Tic-Tac-Toe, using the minimax approach

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame
{
    private JPanel panel = new JPanel();
    private JButton cells[][] = new JButton[3][3];
    private char grid[][] = new char[3][3];         // Holds all the moves played

    public TicTacToe()                      // Default Constructor
    {
        panel.setLayout(new GridLayout(3,3));
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                grid[i][j] = ' ';
                cells[i][j] = new JButton();
                cells[i][j].setBackground(Color.decode("#0b0736"));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#7d7d7d"),2));
                cells[i][j].addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        button_clicked(e);
                    }
                });
                panel.add(cells[i][j]);
            }
        }
        add(panel);
        this.setTitle("Tic-Tac-Toe");
        this.setVisible(true);
    }

    public void button_clicked(ActionEvent e)           // Function which defines the action when a button is clicked
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(e.getSource() == cells[i][j])
                {
                    addMove('x',i,j);
                    cells[i][j].setText(Character.toString(Character.toUpperCase('x')));
                    cells[i][j].setFont(new Font("Serif", Font.BOLD, 32));
                    cells[i][j].setEnabled(false);
                }
            }
        }
        this.playGame();
    }

    public boolean isEmpty(int i , int j)           // Function to check if a given spot is empty
	{
		if(grid[i][j] == ' ')
		{
			return true;
		}
		
        else
        {
            return false;
        }
	}

    public void addMove(char letter , int i , int j)           // Function to add the givem letter to the grid
	{
		if(isEmpty(i,j))
		{
			grid[i][j] = letter;
		}
	}

    public boolean isSpaceLeft()	        // Function to check if there are empty spots on the board
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(isEmpty(i,j))
				{
					return true;
				}
			}
		}
		return false;
	}

    public boolean checkWinner(char letter)		       // Function to check if the given letter has won
	{
		if((grid[0][0] == grid[0][1]) && (grid[0][1] == grid[0][2]) && (grid[0][2] == letter))	// First row
		{
			return true;
		}
		else if((grid[1][0] == grid[1][1]) && (grid[1][1] == grid[1][2]) && (grid[1][2] == letter))	// Second row
		{
			return true;
		}
		else if((grid[2][0] == grid[2][1]) && (grid[2][1] == grid[2][2]) && (grid[2][2] == letter))	// Third row
		{
			return true;
		}
		else if((grid[0][0] == grid[1][0]) && (grid[1][0] == grid[2][0]) && (grid[2][0] == letter))	// First column
		{
			return true;
		}
		else if((grid[0][1] == grid[1][1]) && (grid[1][1] == grid[2][1]) && (grid[2][1] == letter))	// Second column
		{
			return true;
		}
		else if((grid[0][2] == grid[1][2]) && (grid[1][2] == grid[2][2]) && (grid[2][2] == letter))	// Third column
		{
			return true;
		}
		else if((grid[0][0] == grid[1][1]) && (grid[1][1] == grid[2][2]) && (grid[2][2] == letter))	// Leading Diagonal
		{
			return true;
		}
		else if((grid[0][2] == grid[1][1]) && (grid[1][1] == grid[2][0]) && (grid[2][0] == letter))	// Leading Diagonal
		{
			return true;
		}
		return false;
	}

    public int evaluate()	// Evaluation function
	{
	    if(checkWinner('x'))
	    {
			return -10;
		}
		else if(checkWinner('o'))
		{
			return 10;
		}
		else
		{
			return 0;
		}
    }

    public int minimax(int depth , boolean isMax)		// Minimax Function
	{
	    int score = evaluate();
	    if(score == 10)
	        return score;
	    else if(score == -10)
	        return score;
	    else if(!isSpaceLeft())
	        return 0;

	    if(isMax)
	    {
	        int best = -1000;
	        for(int i=0;i<3;i++)
	        {
	            for(int j=0;j<3;j++)
	            {
	                if(isEmpty(i,j))
	                {
	                    addMove('o',i,j);
	                    best = Math.max(best,minimax(depth + 1,!isMax));
	                    grid[i][j] = ' ';
	                }
	            }
	        }
	        return best;
	    }
	    else
	    {
	        int best = 1000;
	        for(int i=0;i<3;i++)
	        {
	            for(int j=0;j<3;j++)
	            {
	                if(isEmpty(i,j))
	                {
	                    addMove('x',i,j);
	                    best = Math.min(best,minimax(depth + 1,!isMax));
	                    grid[i][j] = ' ';
	                }
	            }
	        }
	        return best;
	    }
	}

    public void playBestMove()              // Function to find the best move for the computer
	{
	    int bestVal = -1000;
	    int bestI = -1;
	    int bestJ = -1;

	    for(int i=0;i<3;i++)
	    {
	        for(int j=0;j<3;j++)
	        {
	            if(isEmpty(i,j))
	            {
	                addMove('o',i,j);
	                int moveVal = minimax(0,false);
	  				grid[i][j] = ' ';

	                if(moveVal>bestVal)
	                {
	                    bestI = i;
	                    bestJ = j;
	                    bestVal = moveVal;
	                }
	            }
	        }
	    }
	  	addMove('o',bestI,bestJ);
        cells[bestI][bestJ].setText(Character.toString(Character.toUpperCase('o')));
        cells[bestI][bestJ].setFont(new Font("Serif", Font.BOLD, 32));
        cells[bestI][bestJ].setEnabled(false);
	}

    public void playGame()              // Function which controls the game
	{
		if(isSpaceLeft())
		{
			if(checkWinner('x'))
			{
				JOptionPane.showMessageDialog(rootPane, "Well played ! You won");
                System.exit(0);
			}
			else
			{
				if(isSpaceLeft())
				{
					playBestMove();
					if(checkWinner('o'))
					{
						JOptionPane.showMessageDialog(rootPane, "Well tried ! You lost");
                        System.exit(0);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(rootPane, "It ends in a tie");
                    System.exit(0);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(rootPane, "It ends in a tie");
            System.exit(0);
		}
	}

    public static void main(String Args[])          // Main method
    {
        TicTacToe obj = new TicTacToe();
        obj.setSize(300,300);
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}