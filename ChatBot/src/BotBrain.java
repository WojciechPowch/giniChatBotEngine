import java.io.*;
import java.util.Scanner;
import java.util.*;

public class BotBrain {
    private String path;

    public BotBrain(String p)
    {
        this.path = p;
    }

    private ArrayList<String> toList(String[] arr)
    {
        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < arr.length; i++)
        {
            list.add(arr[i]);
        }
        return list;
    }



    //metoda która porównuje zapytanie do danych zapisnych w pamięci
    private int getMark(String baseCase, String searchedCase)
    {
        String[] baseCaseArr = baseCase.split(" ");
        String[] searchedCaseArr = searchedCase.split(" ");
        ArrayList<String> baseCaseList = toList(baseCaseArr);
        ArrayList<String> searchedCaseList = toList(searchedCaseArr);

        int differentListLength = baseCaseList.size() - searchedCaseList.size();
        String substitute = "%%%%";
        if(differentListLength>=0)
        {
            for(int i = 0; i < differentListLength; i++)
            {
                searchedCaseList.add(substitute);
            }
        }
        else
        {
            for(int i = 0; i < Math.abs(differentListLength); i++)
            {
                baseCaseList.add(substitute);
            }
        }

        ArrayList<Integer> marks = new ArrayList<Integer>();
        int percentOfWord = 0;

        for(int i =0; i < searchedCaseList.size(); i++)
        {
            for(int j = 0; j < baseCaseList.size(); j++ ) {
                int differentStringLength = baseCaseList.get(j).length() - searchedCaseList.get(i).length();
                percentOfWord = 0;

                if (differentStringLength >= 0) {
                    char prototype = '*';
                    String localVar = searchedCaseList.get(i);
                    for (int p = 0; p < differentStringLength; p++) {
                        localVar += String.valueOf(prototype);
                    }
                    // tu skończyłem wczoraj
                }else{
                    char prototype = '~';
                    String localVar = searchedCaseList.get(i);
                    for(int p = 0; p < Math.abs(differentStringLength); p++){
                        localVar += String.valueOf(prototype);
                    }
                }

                for(int c = 0; c < searchedCaseList.get(i).length(); c++){
                    if(searchedCaseList.get(i).charAt(c)==baseCaseList.get(j).charAt(c)){
                        percentOfWord++;
                    }
                }

                double percent = ((double) percentOfWord / (double) searchedCaseList.get(i).length())*100;

                marks.add((int)percent);
            }
        }

        int sum = 0;
        for(int i = 0; i < marks.size(); i++){
            if(marks.get(i)>=60){
                sum++;
            }
        }

        int result = 0;

        if(baseCaseArr.length > searchedCaseArr.length){
            result = (int)((double) sum / (double) baseCaseArr.length)*100;
        }else{
            result = (int)((double) sum / (double) searchedCaseArr.length)*100;
        }

        return result;
    }

    private String answer(String answer, String question, int percentage){
        if(percentage > 65){
            return answer;
        }else{
            return null;
        }
    }

    //metoda w której jest wybierana odpowiedź
    private String chooseAnswer(String question)
    {
        File f = new File(path);
        Scanner scan;
        HashMap<String,String> questionAnswerMap = new HashMap<String,String>();
        try
        {
            scan = new Scanner(f);
            int help = 1;
            int numberOfQuestion = 1;
            int numberOfAnswer = 1;

            while(scan.hasNextLine())
            {
                if(help%2!=0)
                {
                    questionAnswerMap.put("q:"+String.valueOf(numberOfQuestion),scan.nextLine());
                    numberOfQuestion++;
                }
                else
                {
                    questionAnswerMap.put("a:"+String.valueOf(numberOfAnswer),scan.nextLine());
                    numberOfAnswer++;
                }
                help++;
            }
        }catch(FileNotFoundException e)
        {
            e.getMessage();
        }
        HashMap<Integer,Integer> hashWithMarks = new HashMap<Integer, Integer>();

        for(int i = 1; i <= questionAnswerMap.size()/2; i++)
        {
            hashWithMarks.put(i, getMark(questionAnswerMap.get("q:"+String.valueOf(i)),question));
        }

        int max = hashWithMarks.get(1);
        int keyInHash = 0;
        int percentage = 0;

        for(int i = 1; i <= hashWithMarks.size(); i++){
            if(max < hashWithMarks.get(i))
            {
                keyInHash = i;
                percentage = hashWithMarks.get(i);
            }
        }


        //return ma sie odwoływac do metody decision która bezpośrednio na podstawie wyników analizy będzie zwracać string z decyzją
        return answer(questionAnswerMap.get("a:"+String.valueOf(keyInHash)),question, percentage);
    }

    public String getAnswer(String question){
        return chooseAnswer(question);
    }

}
