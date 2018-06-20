import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.io.File;

public class ChatBot {

    public static String remChars(String inp)
    {
        String ret = "";
        char[] list = {'/','+','=','*'};
        inp = inp.toLowerCase();
        for(int i = 0; i < inp.length();i++)
        {
            boolean chek = true;
            for(int j = 0; j < list.length; j++)
            {
                if(inp.charAt(i)==list[j])
                {
                    chek = false;
                }
            }
            if(chek)
            {
                ret+= inp.charAt(i);
            }
            else
            {
                continue;
            }
        }
        return ret;
    }

    public static boolean checkFucks(String question)
    {
        String pathName = "C:\\botBase\\fucks.txt";
        File f = new File(pathName);
        String[] questionArray = question.split(" ");
        ArrayList<String> banWords = new ArrayList<String>();
        banWords.add("kurwa");
        banWords.add("chuj");
        banWords.add("pierd");
        banWords.add("jeb");
        if(f.exists()==false)
        {
            try{
                Formatter formatter = new Formatter(pathName);
                for(int i = 0; i < banWords.size(); i++)
                {
                    formatter.format(banWords.get(i)+"\n");
                }
                formatter.close();
            }catch(Exception e) {
                System.out.println("Error");
            }
        }
        try {
            Scanner fileScanner = new Scanner(f);
            ArrayList<String> banWordsFromFile = new ArrayList<String>();
            while(fileScanner.hasNextLine())
            {
                banWordsFromFile.add(fileScanner.nextLine());
            }
            boolean check = true;
            for(int i = 0; i < questionArray.length; i++)
            {
                for(int j = 0; j < banWordsFromFile.size();j++)
                {
                    if(questionArray[i].contains(banWordsFromFile.get(i))){
                        check = false;
                    }
                }
            }
            return check;
        }catch(FileNotFoundException e)
        {
            e.getMessage();
            return false;
        }
    }

    public static void createBase(String path)
    {
        String pathName = "C:\\botBase\\"+path;
        try{
            Formatter f = new Formatter(pathName);
        }catch(Exception e)
        {
            System.out.println("Error");
        }
    }

    public static ArrayList<String> toList(String pathName)
    {
        try {
            File file = new File(pathName);
            Scanner scan = new Scanner(file);
            ArrayList<String> list = new ArrayList<String>();
            while (scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
            return list;
        }catch(FileNotFoundException e)
        {
            return null;
        }
    }



    public static void addQuestion(String q, String pathName)
    {
        try {

            Scanner sc = new Scanner(System.in);
            ArrayList<String> list = toList(pathName);
            Formatter f = new Formatter(pathName);
            System.out.println("nie wiem jak odpowiedzieć na topytanie, proszę pomóż, wprowadź odpowiedź?");
            q.toLowerCase();
            System.out.println("Wprowadź odpowiedź: ");
            String a = sc.nextLine();
            a.toLowerCase();
            if(checkFucks(a)) {
                list.add(q);
                list.add(a);
                for (int i = 0; i < list.size(); i++) {
                    f.format(list.get(i) + "\r\n");
                }
                f.close();
            }else
            {
                System.out.print("Bot odpowiada: ");
                System.out.print("Nie przeklinaj!");
            }
        }catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String getAnswer(String question,String pathName)
    {
        File x = new File(pathName);
        question = remChars(question);
        try {
            Scanner scan = new Scanner(x);
            ArrayList<String> list = new ArrayList<String>();
            while(scan.hasNextLine())
            {
                list.add(scan.nextLine());
            }

            if(list.contains(question))
                return list.get(list.indexOf(question)+1);
            else
            {
                addQuestion(question,pathName);
                return "Odpowiedź dodano";
            }
        }catch(FileNotFoundException e)
        {
            return e.getMessage().toString();
        }
    }

    public static void main(String[] args)
    {
        System.out.print("Podaj nazwę pliku danych: ");
        Scanner scan = new Scanner(System.in);
        String inp = scan.nextLine();
        String pathName = "C:\\botBase\\"+inp;
        File f = new File(pathName);
        if(f.exists()==false)
        {
            createBase(inp);
        }
        boolean checker = true;
        while(checker)
        {
            System.out.print("Wprowadź pytanie: ");
            String question = scan.nextLine();
            System.out.println("Bot odpowiada: "+getAnswer(question,pathName));
            if(question.equals("Dowidzenia"))
            {
                break;
            }
        }
    }
}
