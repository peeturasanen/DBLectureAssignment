import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeManagementApp extends Application {
    private Label lblFirstName, lblLastName, lblEmail;
    private TextField txtFirstName, txtLastName, txtEmail;
    private Button btnSave;
    private ComboBox<String> cbLanguage;

    // Language data
    private static final String[][] labels = {
            {"First Name:", "Last Name:", "Email:", "Save"},
            {"نام", "نام خانوادگی", "ایمیل", "ذخیره"},
            {"ラベル.名", "ラベル.姓", "ラベル.メール", "保存"}
    };
    private static final String[] languages = {"English", "Farsi", "Japanese"};
    private static final String[] tableNames = {"employee_en", "employee_fa", "employee_ja"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        cbLanguage = new ComboBox<>();
        cbLanguage.getItems().addAll(languages);
        cbLanguage.getSelectionModel().selectFirst();
        lblFirstName = new Label();
        lblLastName = new Label();
        lblEmail = new Label();
        txtFirstName = new TextField();
        txtLastName = new TextField();
        txtEmail = new TextField();
        btnSave = new Button();

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Select Language:"), 0, 0);
        grid.add(cbLanguage, 1, 0);
        grid.add(lblFirstName, 0, 1);
        grid.add(txtFirstName, 1, 1);
        grid.add(lblLastName, 0, 2);
        grid.add(txtLastName, 1, 2);
        grid.add(lblEmail, 0, 3);
        grid.add(txtEmail, 1, 3);
        grid.add(btnSave, 1, 4);

        // Set initial labels based on default language
        updateLabels(0);

        // Language selection change listener
        cbLanguage.setOnAction(e -> updateLabels(cbLanguage.getSelectionModel().getSelectedIndex()));

        // Save button action
        btnSave.setOnAction(e -> {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            int langIndex = cbLanguage.getSelectionModel().getSelectedIndex();
            saveEmployee(firstName, lastName, email, langIndex);
        });

        // Set the scene
        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Update labels based on selected language
    private void updateLabels(int langIndex) {
        lblFirstName.setText(labels[langIndex][0]);
        lblLastName.setText(labels[langIndex][1]);
        lblEmail.setText(labels[langIndex][2]);
        btnSave.setText(labels[langIndex][3]);
    }

    // Save employee data to the corresponding table
    private void saveEmployee(String firstName, String lastName, String email, int langIndex) {
        String tableName = tableNames[langIndex];
        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.insertEmployee(tableName, firstName, lastName, email);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully!");
        alert.showAndWait();
    }
}

class DatabaseHelper {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/db_localization";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Uixu4iso";

    public void insertEmployee(String tableName, String firstName, String lastName, String email) {
        String query = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving employee: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
