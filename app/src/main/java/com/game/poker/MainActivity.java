package com.game.poker;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int HAND_SIZE=5;
    ArrayList<CardDetails> cardList=new ArrayList<>();
    ArrayList<HandRank> handRankList=new ArrayList<>();
    Button dealButton,compareButton;
    ImageView[] imageViews;
    TextView[] textViews;
    ImageView imvP1C1,imvP1C2,imvP1C3,imvP1C4,imvP1C5,imvP2C1,imvP2C2,imvP2C3,imvP2C4,imvP2C5;
    TextView tvP1C1,tvP1C2,tvP1C3,tvP1C4,tvP1C5,tvP2C1,tvP2C2,tvP2C3,tvP2C4,tvP2C5;
    TextView tvP1Status,tvP2Status;
    TextView tvResult;
    String winningHands[]={"Royal Flush","Straight Flush","Four of a Kind","Full House","Flush","Straight","Straight2","Three of a Kind","Two Pair","One Pair","None"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dealButton=(Button)findViewById(R.id.dealButton);
        compareButton=(Button)findViewById(R.id.compareButton);
        imvP1C1=(ImageView)findViewById(R.id.imvP1C1);
        imvP1C2=(ImageView)findViewById(R.id.imvP1C2);
        imvP1C3=(ImageView)findViewById(R.id.imvP1C3);
        imvP1C4=(ImageView)findViewById(R.id.imvP1C4);
        imvP1C5=(ImageView)findViewById(R.id.imvP1C5);
        imvP2C1=(ImageView)findViewById(R.id.imvP2C1);
        imvP2C3=(ImageView)findViewById(R.id.imvP2C3);
        imvP2C2=(ImageView)findViewById(R.id.imvP2C2);
        imvP2C4=(ImageView)findViewById(R.id.imvP2C4);
        imvP2C5=(ImageView)findViewById(R.id.imvP2C5);

        tvP1C1=(TextView)findViewById(R.id.P1Card1Value);
        tvP1C2=(TextView)findViewById(R.id.P1Card2Value);
        tvP1C3=(TextView)findViewById(R.id.P1Card3Value);
        tvP1C4=(TextView)findViewById(R.id.P1Card4Value);
        tvP1C5=(TextView)findViewById(R.id.P1Card5Value);

        tvP2C1=(TextView)findViewById(R.id.P2Card1Value);
        tvP2C2=(TextView)findViewById(R.id.P2Card2Value);
        tvP2C3=(TextView)findViewById(R.id.P2Card3Value);
        tvP2C4=(TextView)findViewById(R.id.P2Card4Value);
        tvP2C5=(TextView)findViewById(R.id.P2Card5Value);

        tvP1Status=(TextView)findViewById(R.id.P1HandValue);
        tvP2Status=(TextView)findViewById(R.id.P2HandValue);

        tvResult=(TextView)findViewById(R.id.ResultValue);
        createImageAndTextViews();
        populateList();
        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo Comment below line if you want to distribute cards using any od the below options
                dealCardsRandomly();
                //ToDo Uncomment below line if you want to distribute Royal Flush
                //dealCardsRoyalFlush();
                //ToDo Uncomment below line if you want to distribute Striaght
                //dealCardsStraight();
                //ToDo Uncomment below line if you want to distribute Four of a Kind
                //dealCardsFourOfAKind();
                //ToDo Uncomment below line if you want to distribute Full House
                //dealCardsFullHouse();
                //ToDo Uncomment below line if you want to distribute Two Pair
                //dealCardsTwoPair();
                //ToDo Uncomment below line if you want to distribute One Pair
                //dealCardsOnePair();
            }
        });
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<HandRank> P1Cards=new ArrayList<HandRank>();
                ArrayList<HandRank> P2Cards=new ArrayList<HandRank>();
                for(int i=0;i<10;i++)
                {
                    if(i<5)
                        P1Cards.add(handRankList.get(i));
                    else
                        P2Cards.add(handRankList.get(i));
                }
                Collections.sort(P1Cards,new CardSorter());
                Collections.sort(P2Cards,new CardSorter());
                String P1Status=getWinner(P1Cards);
                tvP1Status.setText(P1Status);
                String P2Status=getWinner(P2Cards);
                tvP2Status.setText(P2Status);
                System.out.println(P1Status+" "+P2Status);
                int P1=-1,P2=-1;
               for(int i=0;i<winningHands.length;i++)
               {
                   if(winningHands[i].contains(P1Status))
                      P1=i;
                   if(winningHands[i].contains(P2Status))
                       P2=i;
               }
               if(P1<P2)
                   tvResult.setText("Player1's Hand is better!");
               else if(P1>P2)
                   tvResult.setText("Player2's Hand is better!");
               else
               {
                   System.out.println("In draw");
                   boolean aceTie=true;
                   if(P1Cards.get(0).getRank()==1&&P2Cards.get(0).getRank()!=1)
                   {
                       Log.d("TIE","Ace of P1");
                       tvResult.setText("Player1's Hand is better!");
                       aceTie=false;
                   }
                   if(P2Cards.get(0).getRank()==1&&P1Cards.get(0).getRank()!=1)
                   {
                       Log.d("TIE","Ace of P2");
                       tvResult.setText("Player2's Hand is better!");
                       aceTie=false;
                   }
                   if(aceTie)
                   {
                       for(int i=HAND_SIZE-1;i>=0;i--)
                        {
                            if(P1Cards.get(i).getRank()>P2Cards.get(i).getRank()) {
                                tvResult.setText("Player1's Hand is better!");
                                aceTie=false;
                                break;
                            }
                            else if(P1Cards.get(i).getRank()<P2Cards.get(i).getRank()) {
                                tvResult.setText("Player2's Hand is better!");
                                aceTie=false;
                                break;
                            }
                        }
                   }
                   if(aceTie)
                       tvResult.setText("Draw");
               }


            }
        });
    }

    void dealCardsRoyalFlush()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.kingofspades));
        textViews[1].setText("King of Spades");
        rank=getCardRank("King of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.queenofspades));
        textViews[2].setText("Queen of Spades");
        rank=getCardRank("Queen of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.jackofspades));
        textViews[3].setText("Jack of Spades");
        rank=getCardRank("Jack of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofspades));
        textViews[4].setText("Ten of Spades");
        rank=getCardRank("Ten of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));


    }
    void dealCardsRandomly()
    {
        dealButton.setEnabled(false);
        Random random=new Random();
        int n=52,i=0;
        while(n>0)
        {
            if(i==10)
                break;
            int rand=random.nextInt(n);
            n--;
            String name=cardList.get(rand).getName();
            Drawable drawable=cardList.get(rand).getDrawable();
            imageViews[i].setImageDrawable(drawable);
            textViews[i].setText(name);
            int rank[]=getCardRank(name);
            handRankList.add(new HandRank(rank[0],rank[1]));
            cardList.remove(rand);
            i++;
        }

    }
    void dealCardsStraight()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.kingofdiamonds));
        textViews[1].setText("King of Diamonds");
        rank=getCardRank("King of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.queenofhearts));
        textViews[2].setText("Queen of Hearts");
        rank=getCardRank("Queen of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.jackofdiamonds));
        textViews[3].setText("Jack of Diamonds");
        rank=getCardRank("Jack of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofhearts));
        textViews[4].setText("Ten of Hearts");
        rank=getCardRank("Ten of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }
    void dealCardsFourOfAKind()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.aceofclubs));
        textViews[1].setText("Ace of Clubs");
        rank=getCardRank("Ace of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.aceofdiamonds));
        textViews[2].setText("Ace of Diamonds");
        rank=getCardRank("Ace of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.aceofhearts));
        textViews[3].setText("Ace of Hearts");
        rank=getCardRank("Ace of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofspades));
        textViews[4].setText("Ten of Spades");
        rank=getCardRank("Ten of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }
    void dealCardsFullHouse()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.aceofclubs));
        textViews[1].setText("Ace of Clubs");
        rank=getCardRank("Ace of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.aceofdiamonds));
        textViews[2].setText("Ace of Diamonds");
        rank=getCardRank("Ace of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.deuceofclubs));
        textViews[3].setText("Deuce of Hearts");
        rank=getCardRank("Deuce of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.deuceofspades));
        textViews[4].setText("Deuce of Spades");
        rank=getCardRank("Deuce of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }
    void dealCardsThreeOfAKind()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.aceofclubs));
        textViews[1].setText("Ace of Clubs");
        rank=getCardRank("Ace of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.aceofdiamonds));
        textViews[2].setText("Ace of Diamonds");
        rank=getCardRank("Ace of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.deuceofclubs));
        textViews[3].setText("Deuce of Hearts");
        rank=getCardRank("Deuce of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofspades));
        textViews[4].setText("Ten of Spades");
        rank=getCardRank("Ten of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }
    void dealCardsTwoPair()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.aceofspades));
        textViews[0].setText("Ace of Spades");
        rank=getCardRank("Ace of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.aceofclubs));
        textViews[1].setText("Ace of Clubs");
        rank=getCardRank("Ace of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.deuceofdiamonds));
        textViews[2].setText("Deuce of Diamonds");
        rank=getCardRank("Deuce of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.deuceofclubs));
        textViews[3].setText("Deuce of Hearts");
        rank=getCardRank("Deuce of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofspades));
        textViews[4].setText("Ten of Spades");
        rank=getCardRank("Ten of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }
    void dealCardsOnePair()
    {
        dealButton.setEnabled(false);
        int[] rank;
        imageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.fourofspades));
        textViews[0].setText("Four of Spades");
        rank=getCardRank("Four of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.aceofclubs));
        textViews[1].setText("Ace of Clubs");
        rank=getCardRank("Ace of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.deuceofdiamonds));
        textViews[2].setText("Deuce of Diamonds");
        rank=getCardRank("Deuce of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.deuceofclubs));
        textViews[3].setText("Deuce of Hearts");
        rank=getCardRank("Deuce of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[4].setImageDrawable(getResources().getDrawable(R.drawable.tenofspades));
        textViews[4].setText("Ten of Spades");
        rank=getCardRank("Ten of Spades");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[5].setImageDrawable(getResources().getDrawable(R.drawable.eightofclubs));
        textViews[5].setText("Eight of Clubs");
        rank=getCardRank("Eight of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));
        imageViews[6].setImageDrawable(getResources().getDrawable(R.drawable.threeofdiamonds));
        textViews[6].setText("Three of Diamonds");
        rank=getCardRank("Three of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[7].setImageDrawable(getResources().getDrawable(R.drawable.sixofdiamonds));
        textViews[7].setText("Six of Diamonds");
        rank=getCardRank("Six of Diamonds");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[8].setImageDrawable(getResources().getDrawable(R.drawable.jackofhearts));
        textViews[8].setText("Jack of Hearts");
        rank=getCardRank("Jack of Hearts");
        handRankList.add(new HandRank(rank[0],rank[1]));

        imageViews[9].setImageDrawable(getResources().getDrawable(R.drawable.tenofclubs));
        textViews[9].setText("Ten of Clubs");
        rank=getCardRank("Ten of Clubs");
        handRankList.add(new HandRank(rank[0],rank[1]));

    }

    void populateList()
    {
        cardList.add(new CardDetails("Ace of Clubs",getResources().getDrawable(R.drawable.aceofclubs)));
        cardList.add(new CardDetails("Ace of Diamonds",getResources().getDrawable(R.drawable.aceofdiamonds)));
        cardList.add(new CardDetails("Ace of Hearts",getResources().getDrawable(R.drawable.aceofhearts)));
        cardList.add(new CardDetails("Ace of Clubs",getResources().getDrawable(R.drawable.aceofspades)));

        cardList.add(new CardDetails("Deuce of Clubs",getResources().getDrawable(R.drawable.deuceofclubs)));
        cardList.add(new CardDetails("Deuce of Diamonds",getResources().getDrawable(R.drawable.deuceofdiamonds)));
        cardList.add(new CardDetails("Deuce of Hearts",getResources().getDrawable(R.drawable.deuceofhearts)));
        cardList.add(new CardDetails("Deuce of Spades",getResources().getDrawable(R.drawable.deuceofspades)));


        cardList.add(new CardDetails("Three of Clubs",getResources().getDrawable(R.drawable.threeofclubs)));
        cardList.add(new CardDetails("Three of Diamonds",getResources().getDrawable(R.drawable.threeofdiamonds)));
        cardList.add(new CardDetails("Three of Hearts",getResources().getDrawable(R.drawable.threeofhearts)));
        cardList.add(new CardDetails("Three of Spades",getResources().getDrawable(R.drawable.threeofspades)));

        cardList.add(new CardDetails("Four of Clubs",getResources().getDrawable(R.drawable.fourofclubs)));
        cardList.add(new CardDetails("Four of Diamonds",getResources().getDrawable(R.drawable.fourofdiamonds)));
        cardList.add(new CardDetails("Four of Hearts",getResources().getDrawable(R.drawable.fourofhearts)));
        cardList.add(new CardDetails("Four of Spades",getResources().getDrawable(R.drawable.fourofspades)));

        cardList.add(new CardDetails("Five of Clubs",getResources().getDrawable(R.drawable.fiveofclubs)));
        cardList.add(new CardDetails("Five of Diamonds",getResources().getDrawable(R.drawable.fiveofdiamonds)));
        cardList.add(new CardDetails("Five of Hearts",getResources().getDrawable(R.drawable.fiveofhearts)));
        cardList.add(new CardDetails("Five of Spades",getResources().getDrawable(R.drawable.fiveofspades)));

        cardList.add(new CardDetails("Six of Clubs",getResources().getDrawable(R.drawable.sixofclubs)));
        cardList.add(new CardDetails("Six of Diamonds",getResources().getDrawable(R.drawable.sixofdiamonds)));
        cardList.add(new CardDetails("Six of Hearts",getResources().getDrawable(R.drawable.sixofhearts)));
        cardList.add(new CardDetails("Six of Spades",getResources().getDrawable(R.drawable.sixofspades)));

        cardList.add(new CardDetails("Seven of Clubs",getResources().getDrawable(R.drawable.sevenofclubs)));
        cardList.add(new CardDetails("Seven of Diamonds",getResources().getDrawable(R.drawable.sevenofdiamonds)));
        cardList.add(new CardDetails("Seven of Hearts",getResources().getDrawable(R.drawable.sevenofhearts)));
        cardList.add(new CardDetails("Seven of Spades",getResources().getDrawable(R.drawable.sevenofspades)));

        cardList.add(new CardDetails("Eight of Clubs",getResources().getDrawable(R.drawable.eightofclubs)));
        cardList.add(new CardDetails("Eight of Diamonds",getResources().getDrawable(R.drawable.eightofdiamonds)));
        cardList.add(new CardDetails("Eight of Hearts",getResources().getDrawable(R.drawable.eightofhearts)));
        cardList.add(new CardDetails("Eight of Spades",getResources().getDrawable(R.drawable.eightofspades)));

        cardList.add(new CardDetails("Nine of Clubs",getResources().getDrawable(R.drawable.nineofclubs)));
        cardList.add(new CardDetails("Nine of Diamonds",getResources().getDrawable(R.drawable.nineofdiamonds)));
        cardList.add(new CardDetails("Nine of Hearts",getResources().getDrawable(R.drawable.nineofhearts)));
        cardList.add(new CardDetails("Nine of Spades",getResources().getDrawable(R.drawable.nineofspades)));

        cardList.add(new CardDetails("Ten of Clubs",getResources().getDrawable(R.drawable.tenofclubs)));
        cardList.add(new CardDetails("Ten of Diamonds",getResources().getDrawable(R.drawable.tenofdiamonds)));
        cardList.add(new CardDetails("Ten of Hearts",getResources().getDrawable(R.drawable.tenofhearts)));
        cardList.add(new CardDetails("Ten of Spades",getResources().getDrawable(R.drawable.tenofspades)));

        cardList.add(new CardDetails("Jack of Clubs",getResources().getDrawable(R.drawable.jackofclubs)));
        cardList.add(new CardDetails("Jack of Diamonds",getResources().getDrawable(R.drawable.jackofdiamonds)));
        cardList.add(new CardDetails("Jack of Hearts",getResources().getDrawable(R.drawable.jackofhearts)));
        cardList.add(new CardDetails("Jack of Spades",getResources().getDrawable(R.drawable.jackofspades)));

        cardList.add(new CardDetails("Queen of Clubs",getResources().getDrawable(R.drawable.queenofclubs)));
        cardList.add(new CardDetails("Queen of Diamonds",getResources().getDrawable(R.drawable.queenofdiamonds)));
        cardList.add(new CardDetails("Queen of Hearts",getResources().getDrawable(R.drawable.queenofhearts)));
        cardList.add(new CardDetails("Queen of Spades",getResources().getDrawable(R.drawable.queenofspades)));

        cardList.add(new CardDetails("King of Clubs",getResources().getDrawable(R.drawable.kingofclubs)));
        cardList.add(new CardDetails("King of Diamonds",getResources().getDrawable(R.drawable.kingofdiamonds)));
        cardList.add(new CardDetails("King of Hearts",getResources().getDrawable(R.drawable.kingofhearts)));
        cardList.add(new CardDetails("King of Spades",getResources().getDrawable(R.drawable.kingofspades)));

    }
    void createImageAndTextViews()
    {
        imageViews=new ImageView[10];
        imageViews[0]=imvP1C1;
        imageViews[1]=imvP1C2;
        imageViews[2]=imvP1C3;
        imageViews[3]=imvP1C4;
        imageViews[4]=imvP1C5;

        imageViews[5]=imvP2C1;
        imageViews[6]=imvP2C2;
        imageViews[7]=imvP2C3;
        imageViews[8]=imvP2C4;
        imageViews[9]=imvP2C5;

        textViews=new TextView[10];
        textViews[0]=tvP1C1;
        textViews[1]=tvP1C2;
        textViews[2]=tvP1C3;
        textViews[3]=tvP1C4;
        textViews[4]=tvP1C5;

        textViews[5]=tvP2C1;
        textViews[6]=tvP2C2;
        textViews[7]=tvP2C3;
        textViews[8]=tvP2C4;
        textViews[9]=tvP2C5;
    }
    int[] getCardRank(String name)
    {
        int cardNo,cardSuit;
        String cardName=(name.substring(0,name.indexOf(" "))).toLowerCase();
        String cardSuitName=(name.substring(cardName.length()+4)).toLowerCase();

        System.out.println(cardName+","+cardSuitName);
        if(cardSuitName.equalsIgnoreCase("clubs"))
        {
            cardSuit=1;
        }else if(cardSuitName.equalsIgnoreCase("diamonds"))
        {
            cardSuit=2;
        }else if(cardSuitName.equalsIgnoreCase("hearts"))
        {
            cardSuit=3;
        }
        else
        {
            cardSuit=4;
        }
        if(cardName.equalsIgnoreCase("ace"))
            cardNo=1;
        else if(cardName.equalsIgnoreCase("deuce"))
            cardNo=2;
        else if(cardName.equalsIgnoreCase("three"))
            cardNo=3;
        else if(cardName.equalsIgnoreCase("four"))
            cardNo=4;
        else if(cardName.equalsIgnoreCase("five"))
            cardNo=5;
        else if(cardName.equalsIgnoreCase("six"))
            cardNo=6;
        else if(cardName.equalsIgnoreCase("seven"))
            cardNo=7;
        else if(cardName.equalsIgnoreCase("eight"))
            cardNo=8;
        else if(cardName.equalsIgnoreCase("nine"))
            cardNo=9;
        else if(cardName.equalsIgnoreCase("ten"))
            cardNo=10;
        else if(cardName.equalsIgnoreCase("jack"))
            cardNo=11;
        else if(cardName.equalsIgnoreCase("queen"))
            cardNo=12;
        else
            cardNo=13;
        int result[]=new int[2];
        result[0]=cardNo;
        result[1]=cardSuit;
        return result;
    }
    String getWinner(ArrayList<HandRank> al)
    {
        int C1Rank=al.get(0).rank;
        int C2Rank=al.get(1).rank;
        int C3Rank=al.get(2).rank;
        int C4Rank=al.get(3).rank;
        int C5Rank=al.get(4).rank;

        int C1Suit=al.get(0).suit;
        int C2Suit=al.get(1).suit;
        int C3Suit=al.get(2).suit;
        int C4Suit=al.get(3).suit;
        int C5Suit=al.get(4).suit;

        if(C1Rank==1&&C2Rank==10&&C3Rank==11&&C4Rank==12&&C5Rank==13&&
                C1Suit==C2Suit&&C2Suit==C3Suit&&C3Suit==C4Suit&&C4Suit==C5Suit)
            return "Royal Flush";
        if(C1Suit==C2Suit&&C2Suit==C3Suit&&C3Suit==C4Suit&&C4Suit==C5Suit&&
                C1Rank==C2Rank+1&&C2Rank==C3Rank+1&&C3Rank==C4Rank+1&&C4Rank==C5Rank+1)
            return "Straight Flush";
        if(C1Rank==C4Rank||C2Rank==C5Rank)
            return "Four of a Kind";
        if(C1Rank==C2Rank&&C3Rank==C4Rank&&C4Rank==C5Rank||C1Rank==C2Rank&&C2Rank==C3Rank&&C4Rank==C5Rank)
            return "Full House";
        if(C1Suit==C2Suit&&C2Suit==C3Suit&&C3Suit==C4Suit&&C4Suit==C5Suit)
            return "Flush";
        if(C1Rank==C2Rank+1&&C2Rank==C3Rank+1&&C3Rank==C4Rank+1&&C4Rank==C5Rank+1)
            return "Straight";
        if(C1Rank==1&&C2Rank==10&&C3Rank==11&&C4Rank==12&&C5Rank==13)
            return "Straight";
        if(C1Rank==C2Rank&&C2Rank==C3Rank||C2Rank==C3Rank&&C3Rank==C4Rank||C3Rank==C4Rank&&C4Rank==C5Rank)
            return "Three of a Kind";
        if(C1Rank==C2Rank&&C3Rank==C4Rank||C2Rank==C3Rank&&C4Rank==C5Rank)
            return "Two Pair";
        if(C1Rank==C2Rank||C2Rank==C3Rank||C3Rank==C4Rank||C4Rank==C5Rank)
            return "One Pair";
        return "None";
    }
}
class CardSorter implements Comparator<HandRank>
{

    @Override
    public int compare(HandRank handRank, HandRank t1) {
        return handRank.getRank()-t1.getRank();
    }
}
class CardDetails
{
    String name;
    Drawable drawable;
    CardDetails(String name,Drawable drawable) {
        this.name=name;
        this.drawable=drawable;
    }

    public String getName() {
        return name;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
class HandRank
{
    int rank,suit;
    HandRank(int rank,int suit)
    {
        this.rank=rank;
        this.suit=suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }
}
