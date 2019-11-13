package control;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;

import entities.NhanVien;
import entities.UserPassword;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MenuAdminQuanLyControl implements Initializable{
	@FXML private JFXButton btnDangXuat;
	@FXML private JFXButton btnThongTinCaNhan;
	@FXML private JFXButton btnDoiMatKhau;

	@FXML private JFXButton btnHome;

	@FXML private JFXButton btnNhanVien;
	@FXML private MenuItem menuThemNV;
	@FXML private MenuItem menuTimNV;
	@FXML private MenuItem menuThongKeNV;

	@FXML MenuItem menuThemHDV;
	@FXML MenuItem menuThongKeHDV;
	@FXML JFXButton btnHuongDanVien;
	@FXML MenuItem menuTimHDV;

	@FXML private JFXButton btnKhachHang;
	@FXML private MenuItem menuThemKH;
	@FXML private MenuItem menuTimKH;
	@FXML private MenuItem menuThongKeKH;

	@FXML private JFXButton btnHoaDon;
	@FXML private MenuItem menuTimHD;
	@FXML private MenuItem menuThongKeHD;

	@FXML private JFXButton btnTour;
	@FXML private MenuItem menuTimTour;
	@FXML private MenuItem menuThemTour;
	@FXML private MenuItem menuThongKeTour;

	@FXML private AnchorPane paneMain;
	@FXML private Label lblTen;
	@FXML private Label lblChucVu;
	@FXML private Label lblNgay;
	@FXML private JFXNodesList nodeNV;

	//	@FXML private JFXDialog dialogDoiMatKhau;

	private NhanVien nhanvien;
	private UserPassword userPassword;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void initialize(URL location, ResourceBundle resources) {


	}

	public void setValues(NhanVien nv, UserPassword userPassword) {
		this.userPassword = userPassword;
		nhanvien = nv;
		lblTen.setText(nv.getHoTen());
		lblNgay.setText(formatter.format(LocalDate.now()));
	}


	@FXML
	public void handleButtonAction(MouseEvent e) {
//		if(e.getSource() == btnDoiMatKhau) {
//			try {
//				Stage stage = new Stage();
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DoiMatKhau.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				DoiMatKhauControl doiMatKhauControl = fxmlLoader.getController();
//				doiMatKhauControl.setValues(userPassword);
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Đổi mật khẩu");
//				alert.setResizable(false);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.APPLICATION_MODAL);
//				Window window = alert.getDialogPane().getScene().getWindow();
//				window.setOnCloseRequest(event -> window.hide());
//
//				alert.showAndWait();
//				if(doiMatKhauControl.getResult() == true) {
//					Node node = (Node) e.getSource();
//					stage = (Stage) node.getScene().getWindow();
//					stage.close();
//
//					fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DangNhap2.fxml"));
//					Parent root = fxmlLoader.load();
//					Scene scene = new Scene(root);
//					stage.setScene(scene);
//					stage.show();
//				}
//
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnHome) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TrangChu.fxml"));
//				AnchorPane paneTrangChu = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneTrangChu);
//			} catch (Exception e2) {
//
//			}
//		}
//		else if(e.getSource() == btnNhanVien) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyNhanVien.fxml"));
//				AnchorPane paneQuanLyNhanVien = fxmlLoader.load();
//				QuanLyNhanVienControl quanLyNhanVienControl = fxmlLoader.getController();
//				quanLyNhanVienControl.setValues(nhanvien);
//				paneMain.getChildren().setAll(paneQuanLyNhanVien);
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnHuongDanVien) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyHDV.fxml"));
//				AnchorPane paneQuanLyNhanVien = fxmlLoader.load();
//				QuanLyHDVControl quanLyHDVControl = fxmlLoader.getController();
//				quanLyHDVControl.setValues(nhanvien);
//				paneMain.getChildren().setAll(paneQuanLyNhanVien);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnKhachHang) {
//			try {
//				AnchorPane paneQuanLyKhachHang = FXMLLoader.load(getClass().getResource("/gui/QuanLyKhachHang.fxml"));
//				paneMain.getChildren().setAll(paneQuanLyKhachHang);
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnHoaDon) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuanLyHoaDon.fxml"));
//				AnchorPane paneQuanLyHoaDon = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneQuanLyHoaDon);
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnTour) {
//			try {
//				AnchorPane paneQuanLyTour = FXMLLoader.load(getClass().getResource("/gui/QuanLyTour.fxml"));
//				paneMain.getChildren().setAll(paneQuanLyTour);
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//
//		else if(e.getSource() == btnDangXuat) {
//			try {
//				Alert alert = new Alert(AlertType.WARNING);
//				alert.setTitle("Do you want to Log outt?");
//				alert.setContentText("Are you sure?");
//
//				ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
//				ButtonType noBtn = new ButtonType("No", ButtonData.NO);
//
//				alert.getButtonTypes().setAll(yesBtn, noBtn);
//
//				if(alert.showAndWait().get() == yesBtn) {
//					Node node = (Node) e.getSource();
//					Stage stage = (Stage) node.getScene().getWindow();
//					stage.close();
//
//					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/DangNhap2.fxml"));
//					Parent root = loader.load();
//					Scene scene = new Scene(root);
//					stage.setScene(scene);
//					stage.show();
//				}
//				else
//					alert.close();
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == btnThongTinCaNhan) {
//			System.out.println("Thong tin ca nhan");
//			try {
//				Node node = (Node) e.getSource();
//				Stage stage = (Stage) node.getScene().getWindow();
//
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinCaNhan.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				ThongTinCaNhanControl thongTinCaNhanControl = fxmlLoader.getController();	
//				thongTinCaNhanControl.setValues(nhanvien);
//
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Thông tin Nhân viên");
//				alert.setResizable(false);
//				alert.initOwner(stage);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.APPLICATION_MODAL);
//
//				alert.showAndWait();
//				if(thongTinCaNhanControl.getResult() == true) {
//					NhanVienImp nhanVienImp = new NhanVienImp();
//					nhanvien = nhanVienImp.timNhanVien(this.nhanvien.getMaNV());
//					alert(AlertType.INFORMATION, "Cập nhật thành công", "Cập nhật thông tin thành công", "Thông tin của bạn đã được cập nhật lên hệ thống");
//				}
//
//			} catch (Exception e2) {
//
//				e2.printStackTrace();
//			}
//		}	
	}


	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	public void handle(Event e) {

//		if(e.getSource() == menuThemNV) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemNhanVien.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				ThemNhanVienControl themNhanVienControl = fxmlLoader.getController();
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Thêm Nhân Viên");
//				alert.setResizable(false);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.WINDOW_MODAL);
//				Window window = alert.getDialogPane().getScene().getWindow();
//				window.setOnCloseRequest(event -> window.hide());
//
//				alert.showAndWait();
//				if(themNhanVienControl.getResult() == true) {
//					alert.close();
//				}
//			} catch (Exception e2) {
//
//				System.out.println(e2.getMessage());
//				System.out.println("Button Add");
//				e2.printStackTrace();
//
//			}
//		}
//		else if(e.getSource() == menuTimNV) {
//			System.out.println("TimNV");
//		}
//		else if(e.getSource() == menuThongKeNV) {
//			System.out.println("Thong ke NV");
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongKeNhanVien.fxml"));
//				AnchorPane paneQuanLyNhanVien = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneQuanLyNhanVien);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == menuThemHDV) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemHuongDanVien.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				ThemHuongDanVienControl themHDVControl = fxmlLoader.getController();
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Thêm Hướng dẫn viên");
//				alert.setResizable(false);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.APPLICATION_MODAL);
//				Window window = alert.getDialogPane().getScene().getWindow();
//				window.setOnCloseRequest(event -> window.hide());
//
//				alert.showAndWait();
//				if(themHDVControl.getResult() == true) {
//					alert.close();
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
//		}
//		else if(e.getSource() == menuThemKH) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemKhachHang.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				ThemKhachHangControl themKhachHangControl = fxmlLoader.getController();
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Thêm Khách Hàng");
//				alert.setResizable(false);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.APPLICATION_MODAL);
//				Window window = alert.getDialogPane().getScene().getWindow();
//				window.setOnCloseRequest(event -> window.hide());
//
//				alert.showAndWait();
//				if(themKhachHangControl.getResult() == true) {
//					alert.close();
//				}
//			} catch (Exception e2) {
//
//				System.out.println(e2.getMessage());
//				System.out.println("Button Add");
//				e2.printStackTrace();
//
//			}
//		}
//		else if(e.getSource() == menuTimKH) {
//			System.out.println("Tim KH");
//		}
//		else if(e.getSource() == menuThongKeKH) {
//			System.out.println("Thong ke KH");
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongKeKhachHang.fxml"));
//				AnchorPane paneQuanLyKhachHang = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneQuanLyKhachHang);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == menuThemTour) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemTour.fxml"));
//				DialogPane dialogPane = new DialogPane();
//				dialogPane = fxmlLoader.load();
//				ThemTourControl themTourControl = fxmlLoader.getController();
//				Alert alert = new Alert(AlertType.CONFIRMATION);
//				alert.setTitle("Thêm Khách Hàng");
//				alert.setResizable(false);
//				alert.setDialogPane(dialogPane);
//				alert.initModality(Modality.APPLICATION_MODAL);
//				Window window = alert.getDialogPane().getScene().getWindow();
//				window.setOnCloseRequest(event -> window.hide());
//				alert.showAndWait();
//				if(themTourControl.getResult() == true) {
//					alert.close();
//				}
//
//			} catch (Exception e2) {
//
//				System.out.println(e2.getMessage());
//				System.out.println("Button Add");
//				e2.printStackTrace();
//			}
//
//		}
//		else if(e.getSource() == menuTimTour) {
//			System.out.println("tim tout");
//		}
//		else if(e.getSource() == menuThongKeTour) {
//			System.out.println("thong ke tou");
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongKeTour.fxml"));
//				AnchorPane paneQuanLyTour = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneQuanLyTour);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}
//		else if(e.getSource() == menuThongKeHD) {
//			System.out.println("Thong ke HD");
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongKeHoaDon.fxml"));
//				AnchorPane paneQuanLyHoaDon = fxmlLoader.load();
//				paneMain.getChildren().setAll(paneQuanLyHoaDon);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e2.printStackTrace();
//			}
//		}

	}

}
