package com.sutd.zhangzhexian.wekaanalytics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import weka.classifiers.lazy.IBk;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import wlsvm.WLSVM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //To save your trained model to a file:

        //weka.core.SerializationHelper.write(svmModel, svmCls);

        //To load your previously trained model from a file:

        //WLSVM svmCls = (WLSVM) weka.core.SerializationHelper.read(svmModel);

        //Here svmModel is a String variable of the model file.  Do not forget to add the access permission to your app.


        try {
            File f = new File(root,	"iris_train.arff");
            BufferedReader inputReader;
            inputReader	= readFile(f);

            Instances data = new Instances(inputReader);
            data.setClassIndex(data.numAttributes() - 1);

            WLSVM svmCls = new WLSVM();

            svmCls.buildClassifier(data);

            //Classifier ibk = new IBk();
            //ibk.buildClassifier(data);

            String svmModel = root+"/svmTrainingModel";

            weka.core.SerializationHelper.write(svmModel, svmCls);




            //f = new File(root, "iris_test.arff");
            //inputReader = readFile(f);
            //Instances test = new Instances(inputReader);
            //test.setClassIndex(test.numAttributes() - 1);


        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("error");
        }


    }

    public void classify(View view){
        try{

            File root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            WLSVM newsvmCls = new WLSVM();


            newsvmCls = (WLSVM) weka.core.SerializationHelper.read(root+"/svmTrainingModel");

            // Declare four numeric attributes
            Attribute Attribute1 = new Attribute("sepallength");
            Attribute Attribute2 = new Attribute("sepalwidth");
            Attribute Attribute3 = new Attribute("petallength");
            Attribute Attribute4 = new Attribute("petalwidth");

            // Declare the class attribute along with its values (nominal)
            FastVector fvClassVal = new FastVector(3);
            fvClassVal.addElement("Iris-setosa");
            fvClassVal.addElement("Iris-versicolor");
            fvClassVal.addElement("Iris-virginica");
            Attribute ClassAttribute = new Attribute("class", fvClassVal);

            String[] classfierOutputArray = new String[]{"Iris-setosa","Iris-versicolor","Iris-virginica"};

            // Declare the feature vector template
            FastVector fvWekaAttributes = new FastVector(5);
            fvWekaAttributes.addElement(Attribute1);
            fvWekaAttributes.addElement(Attribute2);
            fvWekaAttributes.addElement(Attribute3);
            fvWekaAttributes.addElement(Attribute4);
            fvWekaAttributes.addElement(ClassAttribute);

            // Creating testing instances object with name "TestingInstance"
            // using the feature vector template we declared above
            // and with initial capacity of 1

            Instances testingSet = new Instances("TestingInstance", fvWekaAttributes, 1);

            // Setting the column containing class labels:
            testingSet.setClassIndex(testingSet.numAttributes() - 1);

            // Create and fill an instance, and add it to the testingSet
            Instance iExample = new Instance(testingSet.numAttributes());

            EditText sl = (EditText)findViewById(R.id.slValue);
            double slValue = Double.parseDouble(sl.getText().toString());
            EditText sw = (EditText)findViewById(R.id.swValue);
            double swValue = Double.parseDouble(sw.getText().toString());
            EditText pl = (EditText)findViewById(R.id.plValue);
            double plValue = Double.parseDouble(pl.getText().toString());
            EditText pw = (EditText)findViewById(R.id.pwValue);
            double pwValue = Double.parseDouble(pw.getText().toString());

            iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), slValue);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), swValue);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), plValue);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), pwValue);
            iExample.setValue((Attribute)fvWekaAttributes.elementAt(4),
                    "Iris-setosa"); // dummy

            // add the instance
            testingSet.add(iExample);

            testingSet.setClassIndex(testingSet.numAttributes() - 1);


    //            int correct = 0;
    //            int incorrect = 0;
            for (int i = 0; i < testingSet.numInstances(); i++) {
                double pred = newsvmCls.classifyInstance(testingSet.instance(i));
                //double act = test.instance(i).classValue();
                //if (pred == act)
                //correct++;
                //else
                //incorrect++;
                int index = (int) pred;
                String prediction = classfierOutputArray[index];
                System.out.println(prediction);
                TextView textView = (TextView) findViewById(R.id.textView6);
                textView.setText(prediction);

                Toast.makeText(getApplicationContext(), prediction, Toast.LENGTH_LONG).show();
                };
            }
            catch(Exception e){
                System.out.println(e);
            }



//            TextView textView = (TextView)findViewById(R.id.textView);
//            textView.setText("Correct "+correct+"\nIncorrect "+incorrect);

    }

    private BufferedReader readFile(File f) {
        //	you	need	to	code	the	readFile()
        // that	takes	in	a	File	object	and
        // returns	a	BufferedReader
        BufferedReader in=null;
        try{
            in = new BufferedReader(new FileReader(f));
        }
        catch (FileNotFoundException e){
            System.out.println(e);
            System.out.println("io error");
        }
        return in;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
