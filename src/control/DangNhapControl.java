package control;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.xml.bind.DatatypeConverter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import application.Services;
import entities.KhachHang;
import entities.NhanVien;
import entities.UserPassword;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.KhachHangServices;
import services.NhanVienServices;
import services.UserPasswordServices;

public class DangNhapControl implements Initializable {

	@FXML
	private Label lblError;
	@FXML
	private JFXComboBox<String> cbxTaiKhoan;
	@FXML
	private JFXPasswordField txtPassword;
	@FXML
	private JFXButton btnSignIn;
	@FXML
	private JFXButton btnSignUp;
	@FXML
	private JFXButton btnCloseWindown;
	@FXML
	private JFXCheckBox chkNhoTaiKhoan;
	private String user = null;
	private String password = null;
	private ObservableList<String> taiKhoan = FXCollections.observableArrayList("admin@admin", "dinhdangkhoa",
			"votech99@gmail.com");
	private KhachHang khachHang;
	private UserPassword userPassword;
	private NhanVien nhanVien;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cbxTaiKhoan.getItems().addAll(taiKhoan);
		cbxTaiKhoan.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
			if (newVal != null) {
				Platform.runLater(() -> {
					matKhau(newVal.toString());
				});
			}
		});
		
		init();
	}

	private void init() {
		cbxTaiKhoan.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				cbxTaiKhoan.validate();
		});
		txtPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtPassword.validate();
		});
		btnSignIn.disableProperty()
				.bind((cbxTaiKhoan.valueProperty().isNull().or(txtPassword.textProperty().isEmpty())));

	}

	@FXML
	void handleButtonAction(MouseEvent e) {
		if (e.getSource() == btnSignIn) {
			if (checkUserPassword()) {
				int check = login();
				if (check == 1) {
					try {
						Node node = (Node) e.getSource();
						Stage stage = (Stage) node.getScene().getWindow();
						stage.close();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/KhachHang.fxml"));
						Parent root = loader.load();
						Scene scene = new Scene(root);
						stage.setScene(scene);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							
							@Override
							public void handle(WindowEvent event) {
								// TODO Auto-generated method stub
								Services services = new Services();
								KhachHangServices khachHangServices = services.getKhachHangServices();
								try {
									khachHangServices.dangXuat(khachHang);
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						stage.show();

					} catch (Exception e2) {
						e2.printStackTrace();
						System.out.println(e2.getMessage());
						System.out.println("Error SignIn");
					}
				} else if (check == 0) {
					try {
						Node node = (Node) e.getSource();
						Stage stage = (Stage) node.getScene().getWindow();
						stage.close();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Main.fxml"));
						Parent root = loader.load();
						MainControl mainControl = loader.getController();
						mainControl.setValues(this.nhanVien, this.userPassword);
						Scene scene = new Scene(root);
						stage.setScene(scene);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

							@Override
							public void handle(WindowEvent event) {
								// TODO Auto-generated method stub
								Services services = new Services();
								NhanVienServices nhanVienServices = services.getNhanVienServices();
								try {
									nhanVienServices.dangXuat(nhanVien);
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						stage.show();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				} else {
					lblError.setTextFill(Color.TOMATO);
					lblError.setText("This account is Invalid");
					txtPassword.setText("");
				}
			}
		} else if (e.getSource() == btnSignUp) {
			try {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/DangKy.fxml")));
				stage.setScene(scene);
				stage.show();
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println(e2.getMessage());
				System.out.println("Error Register");
			}
		} else if (e.getSource() == btnCloseWindown) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Do you want to exit?");
			alert.setContentText("Are you sure?");

			ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
			ButtonType noBtn = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if (alert.showAndWait().get() == yesBtn)
				System.exit(0);
			else
				alert.close();
		} else if (e.getSource() == chkNhoTaiKhoan) {

		}
	}

	private boolean checkUserPassword() {
		String user = cbxTaiKhoan.getSelectionModel().getSelectedItem();
		String password = txtPassword.getText().toString();
		if (user.equalsIgnoreCase("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Chưa nhập User name");
			alert.showAndWait();
			return false;
		}
		if (password.equalsIgnoreCase("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Chưa nhập Mật khẩu");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public int login() {
		user = cbxTaiKhoan.getSelectionModel().getSelectedItem();
		System.out.println("User: " + user);
		password = txtPassword.getText().toString();
		System.out.println("Password :" + password);
		Services services = new Services();
		NhanVienServices nhanVienServices = services.getNhanVienServices();
		KhachHangServices khachHangServices = services.getKhachHangServices();
		UserPasswordServices userPasswordServices = services.getUserPasswordServices();

		try {
			System.out.println(hash(password));
			this.userPassword = userPasswordServices.timUserPassword(user, hash(password));
			if (this.userPassword != null) {
				System.out.println("ID" + this.userPassword.getId());
				if (this.userPassword.getId().contains("KH")) {
					this.khachHang = khachHangServices.dangNhap(userPassword.getId());
					if (this.khachHang != null)
						return 1;
				} else {
					this.nhanVien = nhanVienServices.dangNhap(userPassword.getId());
					if (this.nhanVien != null) {
						return 0;
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	private void matKhau(String taiKhoan) {
		switch (taiKhoan) {
		case "admin@admin":
			txtPassword.setText("admin");
			break;
		case "dinhdangkhoa":
			txtPassword.setText("025825273a");
			break;
		case "votech99@gmail.com":
			txtPassword.setText("123456");
			break;
		case "":
			txtPassword.setText("");
			break;
		}
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
//	private void thongTinKhachHang() {
//		ds.xoaViewKhachHang();
//		ds.taoViewThongTinKhachHang(user);
//	}
}
