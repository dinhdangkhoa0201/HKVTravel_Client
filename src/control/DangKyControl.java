package control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.KhachHang;
import entities.NhanVien;
import entities.UserPassword;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.KhachHangServices;
import services.NhanVienServices;

public class DangKyControl implements Initializable {
	@FXML
	private JFXButton btnSignUp;
	@FXML
	private JFXButton btnCancel;
	@FXML
	private JFXButton btnCloseWindown;
	@FXML
	private JFXButton btnSignIn;

	@FXML
	private JFXTextField txtHoTen;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXPasswordField txtMatKhau;
	@FXML
	private JFXPasswordField txtConfirmMatKhau;
	@FXML
	private JFXTextField txtDienThoai;
	@FXML
	private JFXTextField txtDiaChi;
	@FXML
	private Label lblEmailError;
	@FXML
	private Label lblSDTError;

	private List<String> danhSachEmailKH;
	private List<String> danhSachEmailNV;

	private List<String> danhSachSDTKH;
	private List<String> danhSachSDTNV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Services services = new Services();
		NhanVienServices nhanVienServices = services.getNhanVienServices();
		KhachHangServices khachHangServices = services.getKhachHangServices();
		try {
			danhSachEmailNV = nhanVienServices.danhSachEmail();
			danhSachEmailKH = khachHangServices.danhSachEmail();
			System.out.println(danhSachEmailKH);
			System.out.println(danhSachEmailNV);
			danhSachSDTKH = khachHangServices.danhSachSDT();
			danhSachSDTNV = nhanVienServices.danhSachSDT();
			System.out.println(danhSachSDTKH);
			System.out.println(danhSachSDTNV);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init();
		txtEmail.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				if (danhSachEmailNV.contains(newVal) || danhSachEmailKH.contains(newVal)) {
					lblEmailError.setText("Email '" + newVal + "' đã được sử dụng");
				} else {
					lblEmailError.setText("");
				}
				System.out.println(newVal);
			}
		});
		txtDienThoai.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				if (danhSachSDTNV.contains(newVal) || danhSachSDTKH.contains(newVal)) {
					lblSDTError.setText("SĐT '" + newVal + "' đã được sử dụng");
				} else {
					lblSDTError.setText("");
				}
				System.out.println(newVal);
			}
		});
	}

	private void init() {
		txtHoTen.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtHoTen.validate();
		});
		txtEmail.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtEmail.validate();
		});
		txtMatKhau.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtMatKhau.validate();
		});
		txtConfirmMatKhau.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtConfirmMatKhau.validate();
		});
		txtDienThoai.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				txtDienThoai.validate();
		});

		btnSignUp.disableProperty()
				.bind((txtHoTen.textProperty().isEmpty()
						.or(txtEmail.textProperty().isEmpty().or(txtMatKhau.textProperty().isEmpty()
								.or(txtConfirmMatKhau.textProperty().isEmpty().or(txtDienThoai.textProperty().isEmpty())
										.or(lblEmailError.textProperty().isNotEmpty())
										.or(lblSDTError.textProperty().isNotEmpty()))))));
	}

	@FXML
	private void handleButtonAction(MouseEvent e) {
		if (e.getSource() == btnSignUp) {
			if (dangKy() == true) {
				try {
					alert(AlertType.INFORMATION, "SUCCESS", "Thêm Nhân viên thành công", null);
					Node node = (Node) e.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();
					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/DangNhap.fxml")));
					stage.setScene(scene);
					stage.show();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		} else if (e.getSource() == btnSignIn) {
			try {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();

				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/DangNhap.fxml")));
				stage.setScene(scene);
				stage.show();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
		} else if (e.getSource() == btnCancel) {
			try {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
		} else if (e.getSource() == btnCloseWindown) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Do you want to exit?");
			alert.setContentText("Are you sure?");

			ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
			ButtonType noBtn = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if (alert.showAndWait().get() == yesBtn)
				System.exit(0);
			else
				alert.close();
		}

	}

	private boolean dangKy() {
		String hoTen = txtHoTen.getText();
		String email = txtEmail.getText();
		String matKhau = txtMatKhau.getText();
		String dienThoai = txtDienThoai.getText();
		String diaChi = txtDiaChi.getText();
		if (txtDiaChi.getText() == null)
			diaChi = "";
		KhachHang khachHang = new KhachHang("", hoTen, "", null, "", email, dienThoai, diaChi);
		UserPassword userPassword = new UserPassword("", matKhau);
		Services services = new Services();
		KhachHangServices khachHangServices = services.getKhachHangServices();
		try {
			return khachHangServices.themKhachHang(khachHang, userPassword);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
