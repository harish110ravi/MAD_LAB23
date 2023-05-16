package co.il.dynamicbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][]btns;
    Button btnResetGame;
    TextView tvWIn;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvWIn = (TextView)findViewById(R.id.tvUWin);
        tvWIn.setVisibility(View.INVISIBLE);
        btnResetGame = (Button)findViewById(R.id.btnPAgain);
        btnResetGame.setOnClickListener(this);
        btnResetGame.setVisibility(View.INVISIBLE);
        LinearLayout l1 = (LinearLayout)findViewById(R.id.l1);
        //
        btns = new Button[3][3];
        for (int i = 0; i < 3; i++)
        {
            LinearLayout linLay = new LinearLayout(this);
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linLay.setLayoutParams(linearLayoutParams);
            linLay.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++)
            {
                btns[i][j]=new Button(this);
                ViewGroup.LayoutParams layoutParams =new ViewGroup.LayoutParams(300,300);
                btns[i][j].setTextSize(30);
                btns[i][j].setLayoutParams(layoutParams);
                btns[i][j].setOnClickListener(this);
                linLay.addView(btns[i][j]);
            }
            l1.addView(linLay);
        }

    }

    @Override
    public void onClick(View v)
    {
        if (btnResetGame == v)
            this.reset();
        else
            userTurn(v);
    }


    public void userTurn(View v)
    {
        Button btn = (Button) v;
        int r = 1 + (int) (Math.random() * 4);
        if(btn.getText().toString().equals("") && !doWeHaveAWinner())
        {
            btn.setText("O");
            counter++;
            if(doWeHaveAWinner())
            {
            }
            else
            {
                robot();
                doWeHaveAWinner();
            }
        }
    }

    // do we have a winner
    public boolean doWeHaveAWinner()
    {
        if (isWin() > 0)
        {
            if (isWin() == 1)
            {
                tvWIn.setText("O Win");
            }
            else if (isWin() == 2)
            {
                tvWIn.setText("program Win");
            }
            btnResetGame.setVisibility(View.VISIBLE);
            tvWIn.setVisibility(View.VISIBLE);
            return true;
        }
        else if (counter > 8)
        {
            tvWIn.setVisibility(View.VISIBLE);
            tvWIn.setText("No One Win");
            btnResetGame.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    //return
    public int isWin()
    {
        if(counter > 4)
        {
            int j = 0;
            for (int i = 0; i < 3; i++)
            {
                if (btns[i][j].getText().toString().length() > 0 && btns[i][j].getText().toString().equals(btns[i][j + 1].getText().toString()) && btns[i][j].getText().toString().equals(btns[i][j + 2].getText().toString()))
                {
                    if (btns[i][j].getText().toString().equals("O"))
                        return 1;//0 win  - odd counting
                    return 2;//x win
                }
                else if (btns[j][i].getText().toString().length() > 0 && btns[j][i].getText().toString().equals(btns[j + 1][i].getText().toString()) && btns[j][i].getText().toString().equals(btns[j + 2][i].getText().toString())) {
                    if (btns[j][i].getText().toString().equals("O"))
                        return 1;//user win - even win
                    return 2;
                }

            }//end of for
            //checking diagonal
            if (btns[0][0].getText().toString().length() > 0 && btns[0][0].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][0].getText().toString().equals(btns[2][2].getText().toString()))
            {
                if (btns[0][0].getText().toString().equals("O"))
                    return 1;//0 win  - odd counting
                return 2;//x win
            }
            if (btns[0][2].getText().toString().length() > 0 && btns[0][2].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][2].getText().toString().equals(btns[2][0].getText().toString()))
            {
                if (btns[0][2].getText().toString().equals("O"))
                    return 1;//o win  - odd counting
                return 2;
            }
        }
        return -1;//no one win
    }

    //reset game and screen
    public void reset()
    {
        counter = 0;
        btnResetGame.setVisibility(View.INVISIBLE);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
                btns[i][j].setText("");
        }
        tvWIn.setVisibility(View.INVISIBLE);
    }

    //AI
    public void robot()
    {
        if(counter <= 1)
        {
            if (btns[1][1].getText().toString().equals("O"))
                rMiddle();
            else
                rPutOnCorner();
        }
        else if (rWin())
        {
            Log.d("robot", "rWin");
        }
        else if (rBlock())
        {
            Log.d("robot", "rBlock");
        }
        else
        {
            rRandomturn();
            Log.d("robot", "rRandomturn");
        }
        counter++;
    }


    public void rRandomturn()
    {
        Random random = new Random();
        boolean find = false;
        while(!find)
        {
            int col = random.nextInt(10)%3;
            int line = random.nextInt(10)%3;
            if(btns[line][col].getText().toString().equals(""))
            {
                btns[line][col].setText("X");
                Log.d("Random turn", "put on: " + line + ", " + col);
                find = true;
            }
        }
    }

    //put on corner
    public void rPutOnCorner()
    {
        int r = 1 + (int) (Math.random() * 4);
        switch (r)
        {
            case 1:
                if(btns[0][0].getText().toString().equals(""))
                {
                    btns[0][0].setText("X");
                    break;
                }
                else if (btns[0][2].getText().toString().equals(""))
                {
                    btns[0][2].setText("X");
                    break;
                }
                else if (btns[2][0].getText().toString().equals(""))
                {
                    btns[2][0].setText("X");
                    break;
                }
                else if (btns[2][2].getText().toString().equals(""))
                {
                    btns[2][2].setText("X");
                    break;
                }
            case 2:
                if(btns[0][2].getText().toString().equals(""))
                {
                    btns[0][2].setText("X");
                    break;
                }
                else if (btns[0][0].getText().toString().equals(""))
                {
                    btns[0][0].setText("X");
                    break;
                }
                else if (btns[2][0].getText().toString().equals(""))
                {
                    btns[2][0].setText("X");
                    break;
                }
                else if (btns[2][2].getText().toString().equals(""))
                {
                    btns[2][2].setText("X");
                    break;
                }
            case 3:
                if(btns[2][0].getText().toString().equals(""))
                {
                    btns[2][0].setText("X");
                    break;
                }
                else if (btns[0][0].getText().toString().equals(""))
                {
                    btns[0][0].setText("X");
                    break;
                }
                else if (btns[0][2].getText().toString().equals(""))
                {
                    btns[0][2].setText("X");
                    break;
                }
                else if (btns[2][2].getText().toString().equals(""))
                {
                    btns[2][2].setText("X");
                    break;
                }
            case 4:
                if(btns[2][2].getText().toString().equals(""))
                {
                    btns[2][2].setText("X");
                    break;
                }
                else if (btns[0][0].getText().toString().equals(""))
                {
                    btns[0][0].setText("X");
                    break;
                }
                else if (btns[0][2].getText().toString().equals(""))
                {
                    btns[0][2].setText("X");
                    break;
                }
                else if (btns[2][0].getText().toString().equals(""))
                {
                    btns[2][0].setText("X");
                    break;
                }
        }
    }

    //middle
    public void rMiddle()
    {
        int n = 1 + (int) (Math.random() * 4);
        switch (n)
        {
            case 1:
            {
                btns[1][0].setText("X");
                break;
            }
            case 2:
            {
                btns[0][1].setText("X");
                break;
            }
            case 3:
            {
                btns[1][2].setText("X");
                break;
            }
            case 4:
            {
                btns[2][1].setText("X");
                break;
            }
        }
    }

    //block the man
    public boolean rBlock()
    {
        if (rBlockHorizontals())
            return true;
        if (rBlockVerticals())
            return true;
        if (rBlockDiagonals())
            return true;
        return false;
    }

    //block horizontals
    public boolean rBlockHorizontals()
    {
        int count = 0;
        int free = -1;
        boolean loc1 = false, loc2 = false;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                tempBtn = btns[i][j];
                if (tempBtn.getText().toString().equals("O"))
                {
                    if (count != 2)
                    {
                        count++;
                        if (!loc1)
                            loc1 = true;
                        else
                            loc2 = true;
                    }
                    else
                    {
                        count = 0;
                        loc1 = false;
                        loc2 = false;
                        free = -1;
                        continue;
                    }
                }
                else if (tempBtn.getText().toString().equals(""))
                    free = j;
                if (loc1 && loc2 && free != -1)
                {
                    btns[i][free].setText("X");
                    Log.d("r Block Horizontals", "blocked: " + String.valueOf(i + ", " + free));
                    return true;
                }
            }
            count = 0;
            loc1 = false;
            loc2 = false;
            free = -1;
        }
        Log.d("Block Horizontals", "return false");
        return false;
    }

    //block verticals
    public boolean rBlockVerticals()
    {
        int count = 0;
        int freeI = -1, freeJ = -1;
        boolean loc1 = false, loc2 = false;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Log.d("Block Verticals", "i: " + i + ", j: " + j + ", loc1: " + loc1 + ", loc2: " + loc2 + ", freeJ: " + freeJ + "freeI: " + freeI);
                tempBtn = btns[j][i];
                if (tempBtn.getText().toString().equals("O"))
                {
                    if (count != 2)
                    {
                        count++;
                        if (!loc1)
                        {
                            loc1 = true;
                            Log.d("r Block Verticals", "loc1 = : " + i);
                        }
                        else
                        {
                            loc2 = true;
                            Log.d("r Block Verticals", "loc2 = : " + i);
                        }
                    }
                    else
                    {
                        count = 0;
                        loc1 = false;
                        loc2 = false;
                        freeI = -1;
                        freeJ = -1;
                        continue;
                    }
                }
                else if (tempBtn.getText().toString().equals(""))
                {
                    freeI = i;
                    freeJ = j;
                    Log.d("r Block Verticals", "free found: " + String.valueOf(freeJ + ", " + freeI) + " | content: " + tempBtn.getText().toString() + ", is there are x inside: " + tempBtn.getText().toString().equals("X"));
                }
                if (loc1 && loc2 && freeI != -1 && freeJ != -1)
                {
                    btns[freeJ][freeI].setText("X");
                    Log.d("r Block Verticals", "blocked: " + String.valueOf(freeJ + ", " + freeI));
                    return true;
                }
            }
            count = 0;
            loc1 = false;
            loc2 = false;
            freeI = -1;
            freeJ = -1;
        }
        Log.d("Block Verticals", "return false");
        return false;
    }

    //block diagonals
    public boolean rBlockDiagonals()
    {
        if (rBlockDiagonals_TLeftToBRight())
            return true;
        if(rBlockDiagonals_TRightToBLeft())
            return true;
        return false;

    }

    //block diagonals from top left to bottom right
    public boolean rBlockDiagonals_TLeftToBRight()
    {
        int count = 0;
        int loc1 = -1, loc2 = -1, free = -1;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
                tempBtn = btns[i][i];
                if (tempBtn.getText().toString().equals("O"))
                {
                    if (count != 2)
                    {
                        count++;
                        if (loc1 == -1)
                            loc1 = i;
                        else
                            loc2 = i;
                    }
                }
                else if (tempBtn.getText().toString().equals(""))
                    free = i;
                if (loc1 != -1 && loc2 != -1 && free != -1)
                {
                    btns[free][free].setText("X");
                    Log.d("r Block Diagonals TLeftToBRight", "blocked: " + String.valueOf(free + ", " + free));
                    return true;
                }
        }
        Log.d("r Block Diagonals TLeftToBRight", "return false");
        return false;
    }

    //block diagonals from top right to bottom left
    public boolean rBlockDiagonals_TRightToBLeft()
    {
        int count = 0;
        int loc1 = -1, loc2 = -1, freeI = -1, freeJ = -1;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (i + j == 2)
                {
                    tempBtn = btns[i][j];
                    if (tempBtn.getText().toString().equals("O"))
                    {
                        if (count != 2)
                        {
                            count++;
                            if (loc1 == -1)
                                loc1 = i;
                            else
                                loc2 = i;
                        }
                    }
                    else if (tempBtn.getText().toString().equals(""))
                    {
                        freeI = i;
                        freeJ = j;
                    }
                }
                if (loc1 != -1 && loc2 != -1 && freeI != -1 && freeJ != -1)
                {
                    btns[freeI][freeJ].setText("X");
                    Log.d("r Block Diagonals TRightToBLeft", "blocked: " + String.valueOf(freeI + ", " + freeJ));
                    return true;
                }
            }
        }
        Log.d("r Block Diagonals TRightToBLeft", "return false");
        return false;
    }

    //win
    public boolean rWin() // like block (copy & paste) but with two X instead of two O
    {
        if (rWinHorizontals())
            return true;
        if (rWinVerticals())
            return true;
        if (rWinDiagonals())
            return true;
        return false;
    }

    //win horizontals
    public boolean rWinHorizontals()
    {
        Log.d("win horizontal", "start");
        int count = 0;
        int loc1 = -1, loc2 = -1, free = -1;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                tempBtn = btns[i][j];
                if (tempBtn.getText().toString().equals("X"))
                {
                    if (count != 2)
                    {
                        count++;
                        if (loc1 == -1)
                            loc1 = j;
                        else
                            loc2 = j;
                    }
                    else
                    {
                        count = 0;
                        loc1 = -1;
                        loc2 = -1;
                        free = -1;
                        continue;
                    }
                }
                else if (tempBtn.getText().toString().equals(""))
                    free = j;
                if (loc1 != -1 && loc2 != -1 && free != -1)
                {
                    Log.d("win horizontal", "set on " + String.valueOf(i + " " + j));
                    btns[i][free].setText("X");
                    return true;
                }
            }
            count = 0;
            loc1 = -1;
            loc2 = -1;
            free = -1;
        }
        return false;
    }

    //win verticals
    public boolean rWinVerticals()
    {
        int count = 0;
        int freeI = -1, freeJ = -1;
        boolean loc1 = false, loc2 = false;
        Button tempBtn1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                Log.d("'win' Verticals", "i: " + j + ", j: " + i + ", loc1: " + loc1 + ", loc2: " + loc2 + ", freeJ: " + freeJ + "freeI: " + freeI);
                tempBtn1 = btns[j][i];
                Log.d("r win Verticals", "tempBtn1 = : " + tempBtn1.getText().toString());
                if (tempBtn1.getText().toString().equals("X"))
                {
                    Log.d("r win Verticals", "found X. location: " + j + ", " + i + ". content: " + tempBtn1.getText().toString());
                    if (count != 2)
                    {
                        count++;
                        if (!loc1)
                        {
                            loc1 = true;
                            Log.d("r win Verticals", "loc1 = : " + i);
                        }
                        else
                        {
                            loc2 = true;
                            Log.d("r win Verticals", "loc2 = : " + i);
                        }
                    }
                    else
                    {
                        count = 0;
                        loc1 = false;
                        loc2 = false;
                        freeI = -1;
                        freeJ = -1;
                        continue;
                    }
                }
                else if (tempBtn1.getText().toString().equals(""))
                {
                    freeI = i;
                    freeJ = j;
                    Log.d("r win Verticals", "free found: " + String.valueOf(freeJ + ", " + freeI) + " | content: " + tempBtn1.getText().toString() + ", is there are x inside: " + tempBtn1.getText().toString().equals("X"));
                }
                if (loc1 && loc2 && freeI != -1 && freeJ != -1)
                {
                    btns[freeJ][freeI].setText("X");
                    Log.d("r win Verticals", "win on: " + String.valueOf(freeJ + ", " + freeI));
                    return true;
                }
            }
            count = 0;
            loc1 = false;
            loc2 = false;
            freeI = -1;
            freeJ = -1;
        }
        Log.d("win Verticals", "return false");
        return false;
    }

    //win diagonals
    public boolean rWinDiagonals()
    {
        if (rWinDiagonals_TLeftToBRight())
            return true;
        if (rWinDiagonals_TRightToBLeft())
            return true;
        return false;
    }

    //win diagonals top left to bottom right
    public boolean rWinDiagonals_TLeftToBRight()
    {
        int count = 0;
        int loc1 = -1, loc2 = -1, free = -1;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            tempBtn = btns[i][i];
            if (tempBtn.getText().toString().equals("X"))
            {
                if (count != 2)
                {
                    count++;
                    if (loc1 == -1)
                        loc1 = i;
                    else
                        loc2 = i;
                }
            }
            else if (tempBtn.getText().toString().equals(""))
                free = i;
            if (loc1 != -1 && loc2 != -1 && free != -1)
            {
                btns[free][free].setText("X");
                Log.d("r Win Diagonals TLeftToBRight", "win on: " + String.valueOf(free + ", " + free));
                return true;
            }
        }
        return false;
    }

    //win diagonals top right to bottom left
    public boolean rWinDiagonals_TRightToBLeft()
    {
        int count = 0;
        int loc1 = -1, loc2 = -1, freeI = -1, freeJ = -1;
        Button tempBtn;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (i + j == 2)
                {
                    tempBtn = btns[i][j];
                    if (tempBtn.getText().toString().equals("X"))
                    {
                        if (count != 2)
                        {
                            count++;
                            if (loc1 == -1)
                                loc1 = i;
                            else
                                loc2 = i;
                        }
                    }
                    else if (tempBtn.getText().toString().equals(""))
                    {
                        freeI = i;
                        freeJ = j;
                    }
                }
                if (loc1 != -1 && loc2 != -1 && freeI != -1 && freeJ != -1)
                {
                    btns[freeI][freeJ].setText("X");
                    Log.d("r Win Diagonals TRightToBLeft", "win on: " + String.valueOf(freeI + ", " + freeJ));
                    return true;
                }
            }
        }
        return false;
    }
}