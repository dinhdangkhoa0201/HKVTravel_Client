package control;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entities.NhanVien;
import entities.UserPassword;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class MainControl implements Initializable {

	@FXML
	BorderPane border_pane;
	@FXML
	Button btnClose1;
	@FXML
	Button btnDangXuat;
	@FXML
	Button btnThongTinCaNhan;
	@FXML
	Button btnDoiMatKhau;
	@FXML
	JFXButton btnClose;
	@FXML
	Label lblTen;
	@FXML
	Label lblNgay;
	private UserPassword userPassword;
	private NhanVien nhanvien;
	private Stage stage = new Stage();

	public void setValues(NhanVien nv, UserPassword userPassword) {
		this.userPassword = userPassword;
		this.nhanvien = nv;
		lblTen.setText(this.nhanvien.getHoTen());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lblNgay.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	@FXML
	public void handleButtonAction(MouseEvent e) {
		if(e.getSource() == btnClose || e.getSource() == btnClose1) {
			alert(AlertType.CONFIRMATION, "Confirm Exit", "Do you want to exit the HKVTravel", null);
		}
	}
	
	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
