package control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.xml.bind.DatatypeConverter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import application.Services;
import entities.NhanVien;
import entities.UserPassword;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.NhanVienServices;
import services.UserPasswordServices;

public class DoiMatKhauControl implements Initializable{

	@FXML private JFXPasswordField txtMatKhau;
	@FXML private Label lblErrorMatKhau;
	@FXML private JFXPasswordField txtMatKhauMoi;
	@FXML private Label lblErrorMatKhauMoi;
	@FXML private JFXPasswordField txtXacNhan;
	@FXML private Label lblErrorXacNhan;
	@FXML private JFXButton btnXacNhan;
	@FXML private JFXButton btnHuy;
	private NhanVien nhanVien;
	private UserPassword userPassword;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtMatKhau.focusedProperty().addListener((o, oldVal, newVal) -> {
			if(!newVal) {
				if(!hash(txtMatKhau.getText()).equals(userPassword.getPassWord())) {
					lblErrorMatKhau.setText("Mật khẩu hiện tại không đúng");
				} else {
					lblErrorMatKhau.setText("");
				}
			}
		});
		txtMatKhauMoi.focusedProperty().addListener((o, oldVal, newVal) -> {
			if(!newVal) {
				if(hash(txtMatKhauMoi.getText()).equals(userPassword.getPassWord())) {
					lblErrorMatKhauMoi.setText("Mật khẩu mới không được trùng với mật khẩu hiện tại");
				} else {
					lblErrorMatKhauMoi.setText("");
				}
			}
		});
		txtXacNhan.focusedProperty().addListener((o, oldVal, newVal) -> {
			if(!newVal) {
				if(!txtXacNhan.getText().equals(txtMatKhauMoi.getText())) {
					lblErrorXacNhan.setText("Mật khẩu xác nhận không khớp với mật khẩu mới");
				} else {
					lblErrorXacNhan.setText("");
				}
			}
		});
		
		btnXacNhan.disableProperty().bind(lblErrorMatKhau.textProperty().isNotEmpty().or(
				lblErrorMatKhauMoi.textProperty().isNotEmpty().or(
						lblErrorXacNhan.textProperty().isNotEmpty().or(
								txtMatKhau.textProperty().isEmpty().or(txtMatKhauMoi.textProperty().isEmpty().or(txtXacNhan.textProperty().isEmpty()))))));
	}
	
	public void setValues(NhanVien nhanVien, UserPassword userPassword) {
		this.nhanVien = nhanVien;
		this.userPassword = userPassword;
		System.out.println(nhanVien);
	}
	
	@FXML
	private void handleButtonAction(MouseEvent e) {
		if(e.getSource() == btnXacNhan) {
			if(doiMatKhau() == true) {
				alert(AlertType.INFORMATION, "SUCCES", "Đổi mật khẩu thành công!", "Vui lòng đăng nhập lại hệ thống");
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				
				try {

					AnchorPane root = new FXMLLoader(getClass().getResource("/gui/DangNhap.fxml")).load();
					Services services = new Services();
					NhanVienServices nhanVienServices = services.getNhanVienServices();
					nhanVienServices.dangXuat(nhanVien);
					stage.setScene(new Scene(root));
					stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource() == btnHuy) {
			
		}
	}
	
	private boolean doiMatKhau() {
		try {
			Services services = new Services();
			UserPasswordServices userPasswordServices = services.getUserPasswordServices();
			System.out.println(userPassword);
			System.out.println(new UserPassword(userPassword.getId(), hash(txtMatKhauMoi.getText())));
			return userPasswordServices.doiMatKhau(new UserPassword(userPassword.getId(), hash(txtMatKhauMoi.getText())));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

	
	private static Alert alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
		return alert;
	}
	
	private String hash(String matKhau) {
		try {
			return DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(matKhau.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
