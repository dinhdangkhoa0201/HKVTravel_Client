package control;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entities.NhanVien;
import entities.UserPassword;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.NhanVienServices;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import com.jfoenix.controls.JFXButton;

import application.Services;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
	@FXML
	Button btnNhanVien;
	@FXML
	Button btnKhachHang;
	@FXML
	Pane paneCenter;
	@FXML
	Button btnHuongDanVien;
	@FXML
	Button btnTour;
	@FXML
	Button btnTimKiemNhanVien;
	@FXML
	Button btnTimKiemKhachHang;
	@FXML
	Button btnTimKiemTour;

	@FXML Button btnTimKhachHang;

	private UserPassword userPassword;
	private NhanVien nhanVien;
	private String id;
	
	public void setValues(NhanVien nv, UserPassword userPassword) {
		this.userPassword = userPassword;
		this.nhanVien = nv;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblNgay.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	@FXML
	public void handleButtonAction(MouseEvent e) {
		if (e.getSource() == btnClose1) {
			if (alert(AlertType.CONFIRMATION, "Confirm Exit", "Do you want to exit the HKVTravel", null)
					.getResult() == ButtonType.OK) {
				Services services = new Services();
				NhanVienServices nhanVienServices = services.getNhanVienServices();
				try {
					nhanVienServices.dangXuat(this.nhanVien);
					System.exit(0);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		} else if (e.getSource() == btnClose) {
			Services services = new Services();
			NhanVienServices nhanVienServices = services.getNhanVienServices();
			try {
				nhanVienServices.dangXuat(this.nhanVien);
				System.exit(0);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnDangXuat) {
			try {
				if (alert(AlertType.CONFIRMATION, "Confirm Logout", "Do you want to Logout HKVTravel", null)
						.getResult() == ButtonType.OK) {
					Node node = (Node) e.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					AnchorPane root = new FXMLLoader(getClass().getResource("/gui/DangNhap.fxml")).load();
					Services services = new Services();
					NhanVienServices nhanVienServices = services.getNhanVienServices();
					nhanVienServices.dangXuat(nhanVien);
					stage.setScene(new Scene(root));
					stage.show();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnThongTinCaNhan) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinCaNhan.fxml"));
				BorderPane paneThongTinCaNhan = fxmlLoader.load();
				ThongTinCaNhanControl thongTinCaNhanControl = fxmlLoader.getController();
				try {
					Services services = new Services();
					NhanVienServices nhanVienServices = services.getNhanVienServices();
					this.nhanVien = nhanVienServices.timNhanVienByMaNV(nhanVien.getMaNV());
					thongTinCaNhanControl.setValues(nhanVien, userPassword);
					border_pane.setCenter(paneThongTinCaNhan);
				} catch (Exception e2) {
				}
//				paneThongTinCaNhan.setPrefSize(paneCenter.getWidth(), paneCenter.getHeight());
//				paneCenter.getChildren().clear();
//				paneCenter.getChildren().add(paneThongTinCaNhan);

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnDoiMatKhau) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DoiMatKhau.fxml"));
				BorderPane paneDoiMatKhau = fxmlLoader.load();
				DoiMatKhauControl doiMatKhauControl = fxmlLoader.getController();
				try {
					Services services = new Services();
					NhanVienServices nhanVienServices = services.getNhanVienServices();
					this.nhanVien = nhanVienServices.timNhanVienByMaNV(nhanVien.getMaNV());
					doiMatKhauControl.setValues(nhanVien, userPassword);
//					paneDoiMatKhau.setPrefSize(paneCenter.getWidth(), paneCenter.getHeight());
//					paneCenter.getChildren().clear();
//					paneCenter.getChildren().add(paneDoiMatKhau);
					border_pane.setCenter(paneDoiMatKhau);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnNhanVien) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyNhanVien.fxml"));
				BorderPane paneNhanVien = fxmlLoader.load();
				QuanLyNhanVienControl quanLyNhanVienControl = fxmlLoader.getController();
				quanLyNhanVienControl.setValues(nhanVien);
//				paneNhanVien.setPrefSize(paneCenter.getWidth(), paneCenter.getHeight());
//				paneCenter.getChildren().clear();
//				paneCenter.getChildren().add(paneNhanVien);
				border_pane.setCenter(paneNhanVien);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnKhachHang) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyKhachHang.fxml"));
				BorderPane paneNhanVien = fxmlLoader.load();
				border_pane.setCenter(paneNhanVien);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnHuongDanVien) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyHDV.fxml"));
				BorderPane paneHuongDanVien = fxmlLoader.load();
				border_pane.setCenter(paneHuongDanVien);
			} catch (Exception e2) {
			}
		} else if (e.getSource() == btnTour) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyTour.fxml"));
				BorderPane paneTour = fxmlLoader.load();
				border_pane.setCenter(paneTour);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private static Alert alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
		return alert;
	}

}
