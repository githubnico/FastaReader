import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Deviltech on 26.10.2015.
 */
public class UI extends Application {


    ArrayList<Button> buttonList = new ArrayList<Button>();
    ArrayList<CheckBox> checkerList = new ArrayList<CheckBox>();

    @Override
    public void init() throws Exception {
        super.init();
    }

    // Replaces the Main
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Graphics
        BorderPane borderPane = new BorderPane();
        Label descriptionLabel = new Label(myLabels.MAIN_CAPTION);
        Text mainText = new Text();
        HBox buttonBox = new HBox();
        VBox checkerBox = new VBox();
        Font monospaced = Font.font("Monospaced", 12);

        Button buttonSelect = new Button(myLabels.BUTTON_SELECT_ALL);
        Button buttonClear = new Button(myLabels.BUTTON_CLEAR_SELECTION);
        Button buttonApply = new Button(myLabels.BUTTON_APPLY);

        buttonList.add(buttonSelect);
        buttonList.add(buttonClear);
        buttonList.add(buttonApply);

        CheckBox checkerHeaders = new CheckBox(myLabels.CHECKER_HEADERS);
        CheckBox checkerSequences = new CheckBox(myLabels.CHECKER_SEQENCES);
        CheckBox checkerNumbering = new CheckBox(myLabels.CHECKER_NUMBERING);

        checkerList.add(checkerHeaders);
        checkerList.add(checkerSequences);
        checkerList.add(checkerNumbering);

        // Set checkbox true by default
        checkerList.forEach((checker) -> checker.setSelected(true));

        // list of command line arguments
        List<String> args;

        try {
            // get command line arguments
            args = getParameters().getRaw();

            // read in the fasta file with the file path
            FastaReader myFasta = new FastaReader(args.get(0));
            myFasta.readInFasta();

            // refresh text label according to settings
            mainText.setText(refreshText(myFasta));

            // refresh text label on apply button
            buttonApply.setOnAction((value) -> mainText.setText(refreshText(myFasta))
            );
        } catch (Exception e) {
            System.out.println("No correct parameters " + e.getMessage());
            mainText.setText("No correct parameters");
        }


        //set every font character the same size
        mainText.setFont(monospaced);

        buttonClear.setOnAction((value) -> setCheckCheckers(false));
        buttonSelect.setOnAction((value) -> setCheckCheckers(true));


        // add buttons and checkboxes to UI
        buttonBox.getChildren().addAll(buttonSelect, buttonClear, buttonApply);
        checkerBox.getChildren().addAll(checkerHeaders, checkerSequences, checkerNumbering);

        borderPane.setTop(descriptionLabel);
        borderPane.setCenter(mainText);
        borderPane.setBottom(buttonBox);
        borderPane.setRight(checkerBox);

        // Prepare scene
        Scene scene = new Scene(borderPane, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle(myLabels.MAIN_TITLE);

        // show scene
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    // Set all checkers selected/deselected
    public void setCheckCheckers(boolean bool) {
        for (CheckBox checker : checkerList) {
            checker.setSelected(bool);
        }
    }

    // get if all checkers are selected/deselected
    public ArrayList<Boolean> getCheckerBools() {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        for (CheckBox checker : checkerList) {
            result.add(checker.isSelected());
        }
        return result;
    }

    // generates new Text according to Checkbox settings
    public String refreshText(FastaReader myFasta) {
        ArrayList<String> rawText = myFasta.printFasta(getCheckerBools());
        String formattedText = "";

        for (String s : rawText) {
            formattedText += s;
        }
        return formattedText;
    }


}
