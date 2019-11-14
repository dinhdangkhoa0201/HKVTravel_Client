package control;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import entities.NhanVien;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ThongTinCaNhanControl implements Initializable{
	@FXML private JFXTextField txtHoTen;
	
	@FXML private JFXTextField txtEmail; 
	@FXML private JFXTextField txtSoDT;
	@FXML private JFXTextField txtCMND;
	@FXML private JFXComboBox<String> cbxGioiTinh;
	@FXML private JFXTextField txtNgaySinh;
	@FXML private JFXTextField txtDiaChi;
	@FXML private Label lblChucVu;
	
	@FXML private JFXButton btnLuu;
	@FXML private JFXButton btnChinhSuaHoSo;
	

	
	private boolean flag;
	private NhanVien nhanVien;
	private Object btnClose;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cbxGioiTinh.getItems().add("Nam");
		cbxGioiTinh.getItems().add("Nữ");
		
		txtNgaySinh.textProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal.equals("")) {
				
			} else {
				
			}
		});
	}

	public void setValues(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
		System.out.println(nhanVien);
		txtHoTen.setText(nhanVien.getHoTen());
		txtEmail.setText(nhanVien.getEmail());
		txtSoDT.setText((nhanVien.getSoDienThoai() == null) ? "" : nhanVien.getSoDienThoai());
		txtCMND.setText(nhanVien.getCmnd());
		if(!nhanVien.getGioiTinh().equalsIgnoreCase(""))
			cbxGioiTinh.setValue(nhanVien.getGioiTinh());
		txtNgaySinh.setText(nhanVien.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtDiaChi.setText(nhanVien.getDiaChi());
		lblChucVu.setText((nhanVien.getChucVu() == 1) ? "Quản lý" : "Nhân viên");
	}

	@FXML
	private void handleButtonAction(MouseEvent e) {
		if(e.getSource() == btnClose) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Do you want to exit?");
			alert.setContentText("Are you sure?");

			ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
			ButtonType noBtn = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if(alert.showAndWait().get() == yesBtn) {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
			else
				alert.close();
		}
		else if(e.getSource() == btnChinhSuaHoSo) {
			txtHoTen.setEditable(true);
			txtSoDT.setEditable(true);
			txtCMND.setEditable(true);
			cbxGioiTinh.setEditable(true);
			txtNgaySinh.setEditable(true);
			txtDiaChi.setEditable(true);
		}
	}
	
	private boolean capNhatNhanVien() {
		try {
			
			
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getResult() {
		return flag;
	}
	
	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
