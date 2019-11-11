package assignment3_501;

import static java.lang.System.out;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    BigNumber num = new BigNumber();
    
    @FXML private Label label;
    
    @FXML private Label label1;
    
    @FXML private TextField txtNum1;
    
    @FXML private TextField txtNum2;
    
    @FXML private TextField txtResult;
    
    @FXML private Button btnCompute;
    
    @FXML private Button btnQuit;
    
    @FXML private Button btnAdd;
    
    @FXML private Button btnSub;
    
    @FXML private Button btnMul;
    
    @FXML private Button btnGreater;
    
    @FXML private Button btnLess;
    
    @FXML private Button btnEqual;
    
    @FXML 
    private void handleButtonQuitAction(ActionEvent event)
    {
        Platform.exit();
    }
    
    @FXML
    private void handleButtonAdd(){
        num.add();
        label1.setText("+");
    }
    
    @FXML
    private void handleButtonSubtract(){
        num.subtract();
        label1.setText("-");
    }
    
    @FXML
    private void handleButtonMultiply(){
        num.multiply();
        label1.setText("*");
    }
    
    @FXML
    private void handleButtonGreater(){
        num.greater();
        label1.setText(">");
    }
    
    @FXML
    private void handleButtonLess(){
        num.less();
        label1.setText("<");
    }
    
    @FXML
    private void handleButtonEqual(){
        num.equal();
        label1.setText("=");
    }
    
    @FXML
    private void handleButtonCompute(){
        txtResult.setText(num.compute(txtNum1.getText(), txtNum2.getText()));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

class BigNumber {
    
    private String txtNum1;
    private String txtNum2;
    private String operand;
    
    public BigNumber(){
        this.txtNum1 = "";
        this.txtNum2 = "";
        this.operand = null;
    }
    
    public void add(){
        this.operand = "plus";
    }

    public void subtract(){
        this.operand = "subtract";
    }
    
    public void multiply(){
        this.operand = "multiply";
    }
    
    private String catchError(Node a){
        Node tmp = a;
        String error = "Error";
        int dotCount = 0;
        int count = 0;
        
        while(tmp != null){
            
            if(tmp.data.equals('-')){
                if(count > 0){
                    out.println("Error 2");
                    return error;
                }
            }
            
            if(tmp.data.equals('.')){
                dotCount++;
                if(dotCount>1){
                    out.println("Error 3");
                    return error;
                }
            }
            tmp = tmp.next;
            count++;
        }
        
        
        error = "No Error";
        return error;    
    }
    
    public void greater(){
        this.operand = "greater";
    }
    
    public void less(){
        this.operand = "less";
    }
    
    public void equal(){
        this.operand = "equal";
    }
    
    public String compute(String a, String b){
        String answer = "";
        Node digits1 = fill(a);
        Node digits2 = fill(b);
        
        answer = catchError(digits1);
        
        if(answer.equals("Error")){
            return answer;
        }
        
        answer = catchError(digits2);
        
        if(answer.equals("Error")){
            return answer;
        }
        
        digits1 = isZero(digits1);
        digits2 = isZero(digits2);
        
        
        Node digits3;
        
        if (operand.equals("greater")){
            
            if(greaterMethod(digits1, digits2).equals("True")){
                answer = "True";
                return answer;
            } else {
                answer = "False";
                return answer;
            }
            
        }
        
        if (operand.equals("less")){
            
            if(lessMethod(digits1, digits2).equals("True")){
                answer = "True";
                return answer;
            } else {
                answer = "False";
                return answer;
            }
            
        }
        
        if (operand.equals("equal")){
            if(equalsMethod(digits1, digits2).equals("True")){
                answer = "True";
                return answer;
            } else {
                answer = "False";
                return answer;
            }
        }
        
        if (operand.equals("plus")){
            digits3 = addMethod(digits1, digits2);
            answer = toString(digits3);
            return answer;
        }
        
        if(operand.equals("multiply")){
            digits3 = multiplyMethod(digits1, digits2);
            answer = toString(digits3);
            return answer;
        }
        
        if(operand.equals("subtract")){
            digits3 = subtractMethod(digits1, digits2);
            answer = toString(digits3);
            return answer;
        }
        return answer;
    }
    
    private String equalsMethod(Node a, Node b){
            String answer;
            Node digits1 = a;
            Node digits2 = b;
            
            out.println("Digits1 data: " + digits1.data);
            out.print("\nDigits1: ");
            print(digits1);
            out.println("\nDigits2 data: " + digits2.data);
            out.print("\nDigits2: ");
            print(digits2);
            
            
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                digits1 = removeNeg(digits1);
                digits2 = removeNeg(digits2);
            } else if (digits1.data.equals('-')){
                answer = "False";
                return answer;
            } else if (digits2.data.equals('-')){
                answer = "False";
                return answer;
            }
            
            int decPos1 = findDecimal(digits1);
            int decPos2 = findDecimal(digits2);
            
            if(decPos1 == 0){
                out.println("\nD1 size: " + size(digits1));
                addDec(digits1, size(digits1) + 1);
                digits1 = clear0(digits1);
                out.println("\nCleared 0");
                decPos1 = findDecimal(digits1);
                out.print("\nDigits1: ");
                print(digits1);
            }
            
            out.println("\ndecPos1: " + decPos1);
            
            if(decPos2 == 0){
                out.println("\nD2 size: " + size(digits2));
                addDec(digits2, size(digits2) + 1);
                
                digits2 = clear0(digits2);
                out.println("\nCleared 0");
                decPos2 = findDecimal(digits2);
                out.print("\nDigits2: ");
                print(digits2);
            }
            
            out.println("\ndecPos2: " + decPos2);
            
            if(decPos1 != decPos2){
                return "False";
            }
            out.println("Clearing again: ");
            digits1 = clear0(digits1);
            print(digits1);
            out.println("Clearing again: ");
            digits2 = clear0(digits2);
            print(digits2);
            
            out.print("\ndigits 1 cleared: ");
            print(digits1);
            out.print("\ndigits 2 cleared: ");
            print(digits2);
            
            out.print("\nAdd1 front?: ");
            out.print(digits1.data);
            
            if(digits1.data.equals('.')){
                digits1 = addFront(digits1, 1);
                
            }
            
            out.print("\nAdded1: ");
            print(digits1);
            
            out.print("\nAdd2 front?: ");
            out.print(digits2.data);
            
            if(digits2.data.equals('.')){
                digits2 = addFront(digits2, 1);
            }
            
            out.print("\nAdded2: ");
            print(digits2);
            
            decPos1 = findDecimal(digits1);
            decPos2 = findDecimal(digits2);
            
            
            if(size(digits1)>size(digits2)){
                add(digits2, size(digits1)-size(digits2));
            } else if (size(digits2)>size(digits1)){
                add(digits1, size(digits2)-size(digits1));
            }
            
            out.println("\nAdded 0 1: ");
            print(digits1);
            out.println("\nAdded 0 2: ");
            print(digits2);
            
            if(decPos1 != decPos2){
                if(decPos1 > decPos2){
                    answer = "True";
                    return answer;
                } else {
                    answer = "False";
                    return answer;
                }
            }
            
            out.println("DecPos ==");
            int stop = 0;
            for(int i = 0; i < decPos1; i++){
                while(digits1.next != null && stop == 0){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g != h){
                        answer = "False";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    if(digits1.data.equals('.') || digits2.data.equals('.')){
                        stop = 1;
                    }
                    out.println("Debugging");
                }
            }
            
            digits1 = digits1.next;
            digits2 = digits2.next;
            
            
            stop = 0;
            if(digits1 != null || digits2 != null){
                while(stop != 1){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g != h){
                        out.println("True");
                        answer = "False";
                        return answer;
                    } 
                    
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    
                    if(digits1 == null){
                        out.println("digits1 null");
                        stop = 1;
                    }
                    
                    if(digits2 == null){
                        out.println("digits2 null");
                        stop = 1;
                    }
                }
            }
            
            if(digits1 == null && digits2 == null){
                out.println("Both null00");
                answer = "True";
                return answer;
            }
            
            if(digits1 == null){
                while(digits2 != null){
                out.println("digits2 not null");
                String f = String.valueOf(digits2.data);
                int h = Integer.valueOf(f);
                if(h > 0){
                    answer = "False";
                    return answer;
                    }
                
                    digits2 = digits2.next;
                }
            }
            
            if(digits2 == null){
                while(digits1 != null){
                    out.println("digits1 not null");
                    String f = String.valueOf(digits1.data);
                    int h = Integer.valueOf(f);
                    if(h > 0){
                        answer = "False";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                }
            }
            answer = "True";
            return answer;
             
    }
    
    private String greaterMethod(Node a, Node b){
            String answer;
            
            Node digits1 = a;
            Node digits2 = b;
            
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                digits1 = removeNeg(digits1);
                digits2 = removeNeg(digits2);
                answer = equalsMethod(digits1, digits2);
                
                if(answer.equals("True")){
                    return "False";
                }
                
                answer = lessMethod(digits1, digits2);
                return answer;
            }
            
            if(digits1.data.equals('-')){
                answer = "False";
                return answer;
            }
            
            if(digits2.data.equals('-')){
                answer = "True";
                return answer;
            }
            
            out.println("Not negative");
            
            int decPos1 = findDecimal(digits1);
            int decPos2 = findDecimal(digits2);
            
            if(decPos1 == 0){
                out.println("\nD1: " + size(digits1));
                addDec(digits1, size(digits1) + 1);
                decPos1 = findDecimal(digits1);
                out.println("\nDigits1: ");
                print(digits1);
            }
            out.println("\ndecPos1: " + decPos1);
            
            if(decPos2 == 0){
                out.println("\nD1: " + size(digits2));
                addDec(digits2, size(digits2) + 1);
                decPos2 = findDecimal(digits2);
                out.println("\nDigits2: ");
                print(digits2);
            }
            out.println("\ndecPos2: " + decPos2);
            
            digits1 = clear0(digits1);
            digits2 = clear0(digits2);
            
            if(digits1.data.equals('.')){
                digits1 = addFront(digits1, 1);
            }
            
            if(digits2.data.equals('.')){
                digits2 = addFront(digits2, 1);
            }
            
            out.println("\nCleared 0 1: ");
            print(digits1);
            out.println("\nCleared 0 2: ");
            print(digits2);
            
            
            
            decPos1 = findDecimal(digits1);
            decPos2 = findDecimal(digits2);
            
            if(size(digits1)>size(digits2)){
                add(digits2, size(digits1)-size(digits2));
            } else if (size(digits2)>size(digits1)){
                add(digits1, size(digits2)-size(digits1));
            }
            
            out.println("\nAdded 0 1: ");
            print(digits1);
            out.println("\nAdded 0 2: ");
            print(digits2);
            
            if(decPos1 != decPos2){
                if(decPos1 > decPos2){
                    answer = "True";
                    return answer;
                } else {
                    answer = "False";
                    return answer;
                }
            }
            
            out.println("DecPos ==");
            int stop = 0;
            for(int i = 0; i < decPos1; i++){
                while(digits1.next != null && stop == 0){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g > h){
                        answer = "True";
                        return answer;
                    } else if (g < h){
                        answer = "False";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    if(digits1.data.equals('.') || digits2.data.equals('.')){
                        stop = 1;
                    }
                    out.println("Debugging");
                }
            }
            
            digits1 = digits1.next;
            digits2 = digits2.next;
            stop = 0;
            if(digits1 != null || digits2 != null){
                while(stop != 1){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g > h){
                        out.println("True");
                        answer = "True";
                        return answer;
                    } else if (g < h){
                        answer = "False";
                        return answer;
                    }
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    
                    if(digits1 == null){
                        out.println("digits1 null");
                        stop = 1;
                    }
                    if(digits2 == null){
                        out.println("digits2 null");
                        stop = 1;
                    }
                }
            }
            
            if(digits1 == null && digits2 == null){
                out.println("Both null");
                answer = "False";
                return answer;
            }
            
            if(digits1 == null){
                while(digits2 != null){
                out.println("digits2 not null");
                String f = String.valueOf(digits2.data);
                int h = Integer.valueOf(f);
                if(h > 0){
                    answer = "False";
                    return answer;
                    }
                
                    digits2 = digits2.next;
                }
            }
            
            if(digits2 == null){
                while(digits1 != null){
                    out.println("digits1 not null");
                    String f = String.valueOf(digits1.data);
                    int h = Integer.valueOf(f);
                    if(h > 0){
                        answer = "True";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                }
            }
            answer = "False";
            return answer;
    }
    
    private String lessMethod(Node a, Node b){
            String answer;
        
            Node digits1 = a;
            Node digits2 = b;
            
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                digits1 = removeNeg(digits1);
                digits2 = removeNeg(digits2);
                
                answer = equalsMethod(digits1, digits2);
                
                if(answer.equals("True")){
                    return "False";
                }
                
                answer = greaterMethod(digits1, digits2);
                return answer;
            }
            
            if(digits1.data.equals('-')){
                answer = "True";
                return answer;
            }
            
            if(digits2.data.equals('-')){
                answer = "False";
                return answer;
            }
            
            
            int decPos1 = findDecimal(digits1);
            int decPos2 = findDecimal(digits2);
            
            if(decPos1 == 0){
                out.println("\nD1: " + size(digits1));
                addDec(digits1, size(digits1) + 1);
                decPos1 = findDecimal(digits1);
                out.println("\nDigits1: ");
                print(digits1);
            }
            
            out.println("\ndecPos1: " + decPos1);
            
            if(decPos2 == 0){
                out.println("\nD1: " + size(digits2));
                addDec(digits2, size(digits2) + 1);
                decPos2 = findDecimal(digits2);
                out.println("\nDigits2: ");
                print(digits2);
            }
            
            out.println("\ndecPos2: " + decPos2);
            
            if(decPos1 != decPos2){
                if(decPos1 > decPos2){
                    answer = "False";
                    return answer;
                } else {
                    answer = "True";
                    return answer;
                }
            }
            
            digits1 = clear0(digits1);
            digits2 = clear0(digits2);
            
            if(digits1.data.equals('.')){
                digits1 = addFront(digits1, 1);
            }
            
            if(digits2.data.equals('.')){
                digits2 = addFront(digits2, 1);
            }
            
            decPos1 = findDecimal(digits1);
            decPos2 = findDecimal(digits2);
            
            if(size(digits1)>size(digits2)){
                add(digits2, size(digits1)-size(digits2));
            } else if (size(digits2)>size(digits1)){
                add(digits1, size(digits2)-size(digits1));
            }
            
            out.println("\nAdded 0 1: ");
            print(digits1);
            out.println("\nAdded 0 2: ");
            print(digits2);
            
            out.println("DecPos ==");
            int stop = 0;
            for(int i = 0; i < decPos1; i++){
                while(digits1.next != null && stop == 0){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g > h){
                        answer = "False";
                        return answer;
                    } else if (g < h){
                        answer = "True";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    if(digits1.data.equals('.') || digits2.data.equals('.')){
                        stop = 1;
                    }
                    out.println("Debugging");
                }
            }
            
            digits1 = digits1.next;
            digits2 = digits2.next;
            stop = 0;
            
            if(digits1 != null || digits2 != null){
                while(stop != 1){
                    String e = String.valueOf(digits1.data);
                    String f = String.valueOf(digits2.data);
                    int g = Integer.valueOf(e);
                    int h = Integer.valueOf(f);
                    out.println("\ng: " + g);
                    out.println("\nh: " + h);
                    
                    if(g < h){
                        out.println("True");
                        answer = "True";
                        return answer;
                    } else if (g > h){
                        out.println("False");
                        answer = "False";
                        return answer;
                    }
                    digits1 = digits1.next;
                    digits2 = digits2.next;
                    
                    if(digits1 == null){
                        out.println("digits1 null");
                        stop = 1;
                    }
                    if(digits2 == null){
                        out.println("digits2 null");
                        stop = 1;
                    }
                }
            }
            
            if(digits1 == null && digits2 == null){
                out.println("Both null");
                answer = "False";
                return answer;
            }
            
            if(digits1 == null){
                while(digits2 != null){
                out.println("digits2 not null");
                String f = String.valueOf(digits2.data);
                int h = Integer.valueOf(f);
                if(h > 0){
                    answer = "True";
                    return answer;
                    }
                
                    digits2 = digits2.next;
                }
            }
            
            if(digits2 == null){
                while(digits1 != null){
                    out.println("digits1 not null");
                    String f = String.valueOf(digits1.data);
                    int h = Integer.valueOf(f);
                    if(h > 0){
                        answer = "False";
                        return answer;
                    }
                    
                    digits1 = digits1.next;
                }
            }
            answer = "False";
            return answer;
    }
    
    private Node addMethod(Node a, Node b){
            String answer;
        
            //filling
            Node digits1 = a;
            Node digits2 = b;
            Node digits3;
            int negative = 0;
            
            //dealing with negatives
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                digits1 = removeNeg(digits1);
                digits2 = removeNeg(digits2);
                negative = 1;
            }
            
            if(digits1.data.equals('-')){
                Node digitsTmp = digits1;
                digits1 = digits2;
                digits2 = removeNeg(digitsTmp);
                digits3 = subtractMethod(digits1, digits2);
                return digits3;
            }
            
            if(digits2.data.equals('-')){
                digits2 = removeNeg(digits2);
                digits3 = subtractMethod(digits1, digits2);
                return digits3;
            }
            
            out.println("digits 1 filled: ");
            print(digits1);
            out.println("\ndigits 2 filled: ");
            print(digits2);
            
            //finding decimal position
            int decimalPos1 = findDecimal(digits1);
            int decimalPos2 = findDecimal(digits2);
            
            int decimalPos3 = 0;
            
            if(decimalPos1 == 0){
                decimalPos1 = size(digits1)+1;
                addDec(digits1, decimalPos1);
                out.println("\nAdded Decimal1: ");
                print(digits1);
                out.println("\ndecimalPos1: " + decimalPos1);
            }
            
            if(decimalPos2 == 0){
                decimalPos2 = size(digits2)+1;
                addDec(digits2, decimalPos2);
                out.println("\nAdded Decimal2: ");
                print(digits2);
                out.println("\ndecimalPos2: " + decimalPos2);
            }
            
            
            if(decimalPos1 > decimalPos2){
                out.print("\n" + decimalPos1 + ">" + decimalPos2);
                decimalPos3 = decimalPos1;
                digits2 = addFront(digits2, decimalPos1-decimalPos2);
                out.println("\nFront equalized: ");
                print(digits2);
            } else if(decimalPos2 > decimalPos1){
                out.println("\n" + decimalPos2 + ">" + decimalPos1);
                decimalPos3 = decimalPos2;
                digits1 = addFront(digits1, decimalPos2-decimalPos1);
                out.println("\nFront equalized: ");
                print(digits1);
            } else if(decimalPos1 == decimalPos2){
                out.println("\n" + decimalPos1 + "==" + decimalPos2);
                decimalPos3 = decimalPos1;
            }
            
            int length1 = size(digits1);
            int length2 = size(digits2);
            
            if(length1 > length2){
                digits2 = add(digits2, length1-length2);
                out.println("\nBackend equalized: ");
                print(digits2);
            } else if(length2 > length1){
                digits1 = add(digits1, length2-length1);
                out.println("\nBackend equalized: ");
                print(digits1);
            }
            
            //reversing
            digits1 = reverse(digits1);
            digits2 = reverse(digits2);
            out.println("\ndigits 1 reversed: ");
            print(digits1);
            out.println("\ndigits 2 reversed: ");
            print(digits2);
            
            
            
            //removing decimals
            digits1 = removeDec(digits1);
            digits2 = removeDec(digits2);
            out.println("\nDecimals 1 removed: ");
            print(digits1);
            out.println("\nDecimals 2 removed: ");
            print(digits2);
            
            
            
            //performing addition
            int sizeBefore;
            if(size(digits1)>size(digits2)){
                sizeBefore = size(digits1);
            } else {
                sizeBefore = size(digits2);
            }
            digits3 = performAdd(digits1, digits2);
            
            if(size(digits3) > sizeBefore){
                decimalPos3++;
            }
            
            out.println("\nAddition Performed: ");
            print(digits3);
            
            //reversing digits
            digits3 = reverse(digits3);
            out.println("\nReverse Digits 3: ");
            print(digits3);
            
            //adding back digits
            out.println("\ndecimalPos3: " + decimalPos3);
            addDec(digits3, decimalPos3);
            
            out.println("\nDigits added back: ");
            print(digits3);
            
            //set text field
            answer = toString(digits3);
            out.println("\nReturning answer: ");
            out.println(answer);
            
            if (negative == 1){
                digits3 = addNeg(digits3);
            }
            return digits3;
    }
    
    private Node subtractMethod(Node a, Node b){
            String answer;
            
            //filling
            int negative = 0;
            
            Node digits1 = a;
            Node digits2 = b;
            Node digits3 = new Node();
            
            //Both Negative
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                Node digitsTmp = removeNeg(digits2);
                digits2 = removeNeg(digits1);
                digits1 = digitsTmp;
                
                if(greaterMethod(digits2, digits1).equals("True")){
                    digitsTmp = digits1;
                    digits1 = digits2;
                    digits2 = digitsTmp;
                    negative = 1;
                }
            }
            
            //digits2 negative
            if(!digits1.data.equals('-') && digits2.data.equals('-')){
                digits2 = removeNeg(digits2);
                digits3 = addMethod(digits1, digits2);
                return digits3;
            }
            
            //digits1 negative
            if(digits1.data.equals('-') && !digits2.data.equals('-')){
                digits2 = addNeg(digits2);
                digits3 = addMethod(digits1, digits2);
                return digits3;
            }
            
            //Both positive
            if(greaterMethod(digits2, digits1).equals("True")){
                Node digitsTmp = digits1;
                digits1 = digits2;
                digits2 = digitsTmp;
                negative = 1;
            }
            
            out.println("comparing");
            
            out.println("digits 1 filled: ");
            print(digits1);
            out.println("\ndigits 2 filled: ");
            print(digits2);

            //finding decimal position
            int decimalPos1 = findDecimal(digits1);
            int decimalPos2 = findDecimal(digits2);

            int decimalPos3 = 0;

            if (decimalPos1 == 0) {
                decimalPos1 = size(digits1) + 1;
                addDec(digits1, decimalPos1);
                out.println("\nAdded Decimal1: ");
                print(digits1);
                out.println("\ndecimalPos1: " + decimalPos1);
            }

            if (decimalPos2 == 0) {
                decimalPos2 = size(digits2) + 1;
                addDec(digits2, decimalPos2);
                out.println("\nAdded Decimal2: ");
                print(digits2);
                out.println("\ndecimalPos2: " + decimalPos2);
            }

            if (decimalPos1 > decimalPos2) {
                out.print("\n" + decimalPos1 + ">" + decimalPos2);
                decimalPos3 = decimalPos1;
                digits2 = addFront(digits2, decimalPos1 - decimalPos2);
                out.println("\nFront equalized: ");
                print(digits2);
            } else if (decimalPos2 > decimalPos1) {
                out.println("\n" + decimalPos2 + ">" + decimalPos1);
                decimalPos3 = decimalPos2;
                digits1 = addFront(digits1, decimalPos2 - decimalPos1);
                out.println("\nFront equalized: ");
                print(digits1);
            } else if (decimalPos1 == decimalPos2) {
                out.println("\n" + decimalPos1 + "==" + decimalPos2);
                decimalPos3 = decimalPos1;
            }

            int length1 = size(digits1);
            int length2 = size(digits2);

            if (length1 > length2) {
                digits2 = add(digits2, length1 - length2);
                out.println("\nBackend equalized: ");
                print(digits2);
            } else if (length2 > length1) {
                digits1 = add(digits1, length2 - length1);
                out.println("\nBackend equalized: ");
                print(digits1);
            }

            //reversing
            digits1 = reverse(digits1);
            digits2 = reverse(digits2);
            out.println("\ndigits 1 reversed: ");
            print(digits1);
            out.println("\ndigits 2 reversed: ");
            print(digits2);

            //removing decimals
            digits1 = removeDec(digits1);
            digits2 = removeDec(digits2);
            out.println("\nDecimals 1 removed: ");
            print(digits1);
            out.println("\nDecimals 2 removed: ");
            print(digits2);

            //performing addition
            int sizeBefore;
            if (size(digits1) > size(digits2)) {
                sizeBefore = size(digits1);
            } else {
                sizeBefore = size(digits2);
            }
            digits3 = performMinus(digits1, digits2);

            if (size(digits3) > sizeBefore) {
                decimalPos3++;
            }

            out.println("\nMinus Performed: ");
            print(digits3);

            //reversing digits
            digits3 = reverse(digits3);
            out.println("\nReverse Digits 3: ");
            print(digits3);

            //adding back digits
            out.println("\ndecimalPos3: " + decimalPos3);
            addDec(digits3, decimalPos3);

            out.println("\nDigits added back: ");
            print(digits3);
            
            digits3 = clear0(digits3);
            
            if(negative == 1){
                digits3 = addNeg(digits3);
            }
            
            //set text field
            answer = toString(digits3);
            out.println("\nReturning answer: ");
            out.println(answer);
            return digits3;
    }
    
    private Node multiplyMethod(Node a, Node b) {
            //filling
            Node digits1 = a;
            Node digits2 = b;
            
            
            int negative = 0;
            String answer = "";
            if(digits1.data.equals('-') && digits2.data.equals('-')){
                digits1 = removeNeg(digits1);
                digits2 = removeNeg(digits2);
            }
            
            if(digits1.data.equals('-')){
                digits1 = removeNeg(digits1);
                negative = 1;
            }
            
            if(digits2.data.equals('-')){
                digits2 = removeNeg(digits2);
                negative = 1;
            }
            
            out.println("digits 1 filled: ");
            print(digits1);
            out.println("\ndigits 2 filled: ");
            print(digits2);
            
            //finding decimal position
            int decimalPos1 = findDecimal(digits1);
            int decimalPos2 = findDecimal(digits2);
            out.println("Decimal Places: ");
            out.println("\nD1: " + decimalPos1);
            out.println("\nD2: " + decimalPos2);
            
            if(decimalPos1 == 0){
                decimalPos1 = size(digits1)+1;
                addDec(digits1, decimalPos1);
                out.println("\nAdded Decimal1: ");
                print(digits1);
                out.println("\ndecimalPos1: " + decimalPos1);
            }
            
            if(decimalPos2 == 0){
                decimalPos2 = size(digits2)+1;
                addDec(digits2, decimalPos2);
                out.println("\nAdded Decimal2: ");
                print(digits2);
                out.println("\ndecimalPos2: " + decimalPos2);
            }
            
            int decimalPosTmp;
            
            int prevent = 0;
            
            if(decimalPos1 == size(digits1) && decimalPos2 == size(digits2)){
                prevent = 1;
                decimalPosTmp = 2;
                out.println("nope");
            } else {
                decimalPosTmp = (size(digits1) - decimalPos1) + (size(digits2) - decimalPos2);
            }
            
            out.println("\nDPT  found: " + decimalPosTmp);
            
            
            
            
            //reversing
            digits1 = reverse(digits1);
            digits2 = reverse(digits2);
            out.println("\ndigits 1 reversed: ");
            print(digits1);
            out.println("\ndigits 2 reversed: ");
            print(digits2);
            
            
            
            //removing decimals
            digits1 = removeDec(digits1);
            digits2 = removeDec(digits2);
            out.println("\nDecimals 1 removed: ");
            print(digits1);
            out.println("\nDecimals 2 removed: ");
            print(digits2);
            
            //performing multiplication
            Node nodeAnswer = performMultiplication(digits1, digits2);
            out.println("\nPerformed Multiplication");
            print(nodeAnswer);
            
            //Filling numbers
            nodeAnswer = nodeAnswer.next;
            Node number1 = new Node(nodeAnswer.data);
            nodeAnswer = nodeAnswer.next;
            int i;
            out.println("\nDecimal Pos Tmp: " + decimalPosTmp);
            
            int nodeAnswerData;
            
            while(!nodeAnswer.data.equals('+')){
                String nodeData = nodeAnswer.data.toString();
                nodeAnswerData = Integer.valueOf(nodeData);
                number1 = addLast(number1, nodeAnswerData);
                nodeAnswer = nodeAnswer.next;
            }
            
            out.println("\nnumber 1: ");
            print(number1);
            out.println("\nNode Answer: ");
            print(nodeAnswer);
            
            nodeAnswer = nodeAnswer.next;
            
            int count = 2;
        while(nodeAnswer!=null){
        
            
            out.println("\ncount: " + count);
            
            Node number2 = new Node(nodeAnswer.data);
            nodeAnswer = nodeAnswer.next;
            out.println("decimalPosTmp: " + decimalPosTmp);
            
            
            
            out.println("count: " + count);
            
            while(!nodeAnswer.data.equals('+')){
                String nodeData = nodeAnswer.data.toString();
                nodeAnswerData = Integer.valueOf(nodeData);
                number2 = addLast(number2, nodeAnswerData);
                nodeAnswer = nodeAnswer.next;
            }
            
            out.println("\nNumber1: ");
            print(number1);
            out.println("\nnumber 2: ");
            print(number2);
            out.println("\nNode Answer: ");
            print(nodeAnswer);
            
            if(size(number2) > size(number1)){
                while(size(number2) > size (number1)){
                number1 = addLast(number1, 0);
                }
            }
            
            out.println("Equalizing: ");
            print(number1);
            
            out.println("Performing add: ");
            
            number1 = performAdd(number1, number2);
            nodeAnswer = nodeAnswer.next;
            
            out.println("\nnumber 1: ");
            print(number1);
            out.println("\nNode Answer: ");
            print(nodeAnswer);
            
            count++;
            }
        
            //reversing digits
            number1 = reverse(number1);
            out.println("\nReverse Answer: ");
            print(number1);
            
            //clearing 0's
            number1 = clear0(number1);
            
            //adding back digits
            if(prevent != 1){
                out.println("\ndecimalPos3: " + decimalPosTmp);
                addDec(number1, size(number1) - decimalPosTmp + 1);
                out.println("\nDigits added back: ");
            }
            
            out.println("\nAnswer: ");
            
            number1 = clear0(number1);
            
            print(number1);
            if(negative == 1){
                number1 = addNeg(number1);
            }

            //set text field
            return number1;
    }
    
    private Node performAdd(Node n1, Node n2){
        
        int place = 0;
        int txtLength = size(n1);
        
        out.println("\nN1 size: " + size(n1));
        print(n1);
        out.println("\nN2 size: " + size(n2));
        print(n2);
        int i = 1;
        int a;
        int b;
        int c;
        Node digitAdd = new Node();
        while(i<=txtLength){
            a = getDigit(n1, i);
            b = getDigit(n2, i);
            c = a+b;
            if(c+place > 9){
                c += place;
                c = (c%10);
                System.out.print("\n");
                place = 1;
            } else {
                c += place;
                place = 0;
            }
            
            if(i == 1){
                digitAdd = new Node(c);
                i++;
            } else {
                digitAdd = addLast(digitAdd, c);
                i++;
            }
            out.println("DigitAdd");
            print(digitAdd);
            
        }
        
        if(place == 1){
            digitAdd = addLast(digitAdd, place);
            print(digitAdd);
        }
        return digitAdd;
    }
    
    private Node performMinus(Node n1, Node n2) {

        int place = 0;
        int txtLength = size(n1);
        int i = 1;
        int a;
        int b;
        int c;
        int borrowed = 0;
        Node digitMinus = new Node();
        while (i <= txtLength) {
            a = getDigit(n1, i) - borrowed;
            b = getDigit(n2, i);

            if (a >= b) {

                c = a - b;

                if (c + place > 9) {
                    c += place;
                    c = (c % 10);
                    System.out.print("\n");
                    place = 1;
                } else {
                    c += place;
                    place = 0;
                }

                if (i == 1) {
                    digitMinus = new Node(c);
                    i++;
                } else {
                    digitMinus = addLast(digitMinus, c);
                    i++;
                }
                borrowed = 0;
            } else {

                c = a + 10 - b;
                if (c + place > 9) {
                    c += place;
                    c = (c % 10);
                    System.out.print("\n");
                    place = 1;
                } else {
                    c += place;
                    place = 0;
                }

                if (i == 1) {
                    digitMinus = new Node(c);
                    i++;
                } else {
                    digitMinus = addLast(digitMinus, c);
                    i++;
                }
                borrowed = 1;
            }

        }

        if (place == 1) {
            digitMinus = addLast(digitMinus, place);
        }

        return digitMinus;

    }
    
    private Node performMultiplication(Node a, Node b){
        Node tmpD2 = b;
        Node answer = new Node("");
        int place = 0;
        int placeHolder = 0;
        int zero = 0;
        while(tmpD2 != null){
            Node tmpD1 = a;
            int i = zero;
            while(tmpD1 != null){
                
                out.println("\ni: " + i);
                
                while(i > 0){
                    out.println("\n" + i + " While Adding Zero");
                    addLast(answer, 0);
                    print(answer);
                    i--;
                }
                

                String n1String = tmpD1.data.toString();
                String n2String = tmpD2.data.toString();
                int n1tmp = Integer.valueOf(n1String);
                int n2tmp = Integer.valueOf(n2String);
                placeHolder = n1tmp * n2tmp + place;
                place = placeHolder/10;
                placeHolder = placeHolder - place*10;
                addLast(answer, placeHolder);
                tmpD1 = tmpD1.next;
                i--;
            }
            if(place > 0){
                addLast(answer, place);
            }
            place = 0;
            tmpD2 = tmpD2.next;
            addLast(answer, '+');
            out.print("\nAnswer: ");
            print(answer);
            zero++;
        }
        return answer;
    }
    
    private Node fill(String a){
        int length = a.length();
        int i = 0;
        Node tmp = new Node(a.charAt(i));
        Node tmp2 = tmp;
        i++;
        
        while(i<length){
            while(tmp2.next != null){
                tmp2 = tmp2.next;
            }
            tmp2.next = new Node(a.charAt(i));
            i++;
        }
        return tmp;
    }
    
    private Node removeNeg(Node l ){
        Node tmp = l;
        tmp = tmp.next;
        return tmp;
    }
    
    private Node clear0(Node l){
        Node tmp = l;
        while(tmp.data.equals('0')){
            tmp = tmp.next;
            out.println("Clearing 0");
        }
        out.println("Finally Cleared: ");
        print(tmp);
        return tmp;
    }
    
    private int getDigit(Node p, int i){
        int j;
        Node tmp = p;
        for(int u = 1; u<i; u++){
            tmp = tmp.next;
        }
        String a = tmp.data.toString();
        j = Integer.valueOf(a);
        return j;
    }
    
    private Node addLast(Node l, int c){
        Node tmp = l;
        if(tmp == null){
            Node tmp1 = new Node(c);
            return tmp1;
        }
        Node current1 = l;
        Node current2 = current1;
        while(current2.next != null){
            current2 = current2.next;
        }
        current2.next = new Node(c);
        return current1;
    }
    
    private Node addLast(Node l, char c){
        if(l == null){
            l = new Node(c);
            return l;
        }
        Node current1 = l;
        Node current2 = current1;
        while(current2.next != null){
            current2 = current2.next;
        }
        current2.next = new Node(c);
        return current1;
    }
    
    private Node addLast(Node l, String c){
        int j = Integer.valueOf(c);
        if(l == null){
            l = new Node(j);
            return l;
        }
        Node current1 = l;
        Node current2 = current1;
        while(current2.next != null){
            current2 = current2.next;
        }
        current2.next = new Node(j);
        return current1;
    }
    
    private Node add(Node l, int num){
        if(num == 0){
            return l;
        }
        
        Node current1 = l;
        Node current2 = current1;
        while(current2.next != null){
            current2 = current2.next;
        }
        current2.next = new Node(0);
        
        current2 = add(current2, num-1);
        
        return current1;
    }
    
    private Node addNeg(Node l){
        Node atmp;
            
        Node n = new Node('-');
        n.next = l;
        atmp = n;
        
        return atmp;
    }
    
    private int size(Node l){
        int p = 0;
        while(l != null){
            p++;
            l = l.next;
        }
        return p;
    }
    
    private Node addDec(Node p, int pos){
        Node tmp1 = p;
        Node tmp2 = p;
        if(pos == 1){
            tmp1 = new Node('.', p);
            return tmp1;
        }
        
        tmp2 = tmp2.next;
        for(int i = 1; i < pos - 1; i++){
            tmp1 = tmp1.next;
            tmp2 = tmp2.next;
        }
        
        tmp1.next = new Node('.', tmp2);
        
        return tmp1;
    }
    
    private Node removeDec(Node p){

            Node tmp = new Node(0);
            tmp.next = p;
            Node tmp2 = tmp;
 
            while(tmp2.next != null){
                if(tmp2.next.data.equals('.')){
                Node next = tmp2.next;
                tmp2.next = next.next; 
            }else{
                tmp2 = tmp2.next;
            }
        }
 
        return tmp.next;



    }
    
    private void print(Node p){
        while(p != null){
                System.out.print(p.data);
                p = p.next;
            }
    }
    
    private int findDecimal(Node decNode){
        int stop = 0;
        int pos = 0;
        Node tmp1 = decNode;
        
        while(stop == 0) {
            if(tmp1.data.equals('.')){
                stop = 1;
                pos++;
            } else {
                if(tmp1.next == null){
                    stop = 1;
                    pos = 0;
                } else {
                    tmp1 = tmp1.next;
                    pos++;
                }
            }
        }
        
        return pos;
        
    }
    
    private Node addFront(Node addF, int num){
        if(num == 0){
            return addF;
        }
        
        Node atmp;
            
        Node n = new Node(0);
        n.next = addF;
        atmp = n;
        
        atmp = addFront(atmp, num-1);
        
        return atmp;
    }
    
    private Node reverse (String reverseNode){
        //        Reversing
        String txtNum = reverseNode;
        
        int txtNumLength = txtNum.length();
        
        int i = txtNumLength - 1;
            
        Node reverse = new Node(txtNum.charAt(i));
        
        Node reverse1 = reverse;
        
        i--;
        
        while(i >= 0){
            while(reverse1.next != null){
                reverse1 = reverse1.next;
            }
            reverse1.next = new Node(txtNum.charAt(i));
            i--;
        }
        
        return reverse;
        
    }
    
    private Node reverse (Node reverseNode){
        Node rev = null;
        Node current = reverseNode;
        while (current != null) {
            Node next = current.next;
            current.next = rev;
            rev = current;
            current = next;
        }
        return rev;  

    }
    
    public String toString(Node p){
        String s = "";
        while(p != null){
            s = s + String.valueOf(p.data);
            p = p.next;
        }
        return s;
    }
    
    private Node isZero (Node l){
        Node tmp = l;
        Node a = new Node(0);
        
        if(tmp.data.equals('-')){
            tmp = tmp.next;
            out.println("- removed, : ");
            print(tmp);
        }
        
        while(tmp.data.equals('-') || tmp.data.equals('0')){
            tmp = tmp.next;
        }
        
        if(tmp == null){
            return a;
        }
        
        return l;
    }
}

class Node<T> {

    public T data;
    public Node<T> next;
    
    // Constructor 0
    public Node() {
        this.data = null;
        this.next = null;
    }

    // Constructor 1

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    // Constructor 2

    public Node(T data, Node next) {
        this.data = data;
        this.next = next;
    }
} // end class

