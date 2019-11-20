package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.KhachHang;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.KhachHangServices;
import services.NhanVienServices;

public class ThongTinKhachHangControl implements Initializable {

	@FXML
	JFXButton btnCapNhat;
	@FXML
	JFXButton btnXoa;
	@FXML
	JFXButton btnClose;

	@FXML
	private TextField txtHoTen;
	@FXML
	private Label lblErrorHoTen;
	@FXML
	private JFXComboBox<String> cbxGioiTinh;
	@FXML
	private Label lblErrorGioiTinh;
	@FXML
	private JFXTextField txtNgaySinh;
	@FXML
	private Label lblErrorNgaySinh;
	@FXML
	private JFXTextField txtNgayVaoLam;
	@FXML
	private JFXTextField txtCMND;
	@FXML
	private Label lblErrorCMND;
	@FXML
	private TextField txtEmail;
	@FXML
	private Label lblErrorEmail;
	@FXML
	private TextField txtDiaChi;
	@FXML
	private Label lblErrorDiaChi;
	@FXML
	private TextField txtSoDienThoai;
	@FXML
	private Label lblErrorSoDienThoai;
	@FXML
	private ImageView imgAnhDaiDien;

	private boolean flag = false;
	private KhachHang khachHang;

	private List<String> danhSachEmailKH;
	private List<String> danhSachEmailNV;

	private List<String> danhSachSDTKH;
	private List<String> danhSachSDTNV;

	private List<String> danhSachCMNDKH;
	private List<String> danhSachCMNDNV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cbxGioiTinh.getItems().add("Nam");
		cbxGioiTinh.getItems().add("Nữ");
	}

	public void setValues(KhachHang khachHang) {
		this.khachHang = khachHang;
		txtHoTen.setText(khachHang.getHoTenKH());
		if (!khachHang.getGioiTinh().equalsIgnoreCase(""))
			cbxGioiTinh.setValue(khachHang.getGioiTinh());
		txtNgaySinh.setText(khachHang.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtCMND.setText(khachHang.getCmnd());
		txtEmail.setText(khachHang.getEmail());
		txtDiaChi.setText(khachHang.getDiaChi());
		txtSoDienThoai.setText(khachHang.getSoDienThoai());
		if (cbxGioiTinh.getValue().equals("Nam")) {
			try {
				File file = new File("src/img/man.png");
				FileInputStream inputStream = new FileInputStream(file);
				Image image = new Image(inputStream);
				imgAnhDaiDien.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				File file = new File("src/img/girl.png");
				FileInputStream inputStream = new FileInputStream(file);
				Image image = new Image(inputStream);
				imgAnhDaiDien.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Services services = new Services();
		NhanVienServices nhanVienServices = services.getNhanVienServices();
		KhachHangServices khachHangServices = services.getKhachHangServices();
		try {
			danhSachEmailNV = nhanVienServices.danhSachEmail();
			danhSachEmailKH = khachHangServices.danhSachEmail();
			danhSachSDTKH = khachHangServices.danhSachSDT();
			danhSachSDTNV = nhanVienServices.danhSachSDT();
			danhSachCMNDKH = khachHangServices.danhSachCMND();
			danhSachCMNDNV = nhanVienServices.danhSachCMND();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (danhSachEmailKH.contains(khachHang.getEmail())) {
			danhSachEmailKH.remove(khachHang.getEmail());
		} else if (danhSachEmailNV.contains(khachHang.getEmail())) {
			danhSachEmailNV.remove(khachHang.getEmail());
		}

		if (danhSachSDTKH.contains(khachHang.getSoDienThoai())) {
			danhSachSDTKH.remove(khachHang.getSoDienThoai());
		} else if (danhSachSDTNV.contains(khachHang.getSoDienThoai())) {
			danhSachSDTNV.remove(khachHang.getSoDienThoai());
		}

		if (danhSachCMNDKH.contains(khachHang.getCmnd())) {
			danhSachCMNDKH.remove(khachHang.getCmnd());
		} else if (danhSachCMNDNV.contains(khachHang.getCmnd())) {
			danhSachCMNDNV.remove(khachHang.getCmnd());
		}

		init();
	}

	private void init() {
		txtHoTen.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtHoTen.getText().equals("")) {
					String regex = "\\d+";
					if (txtHoTen.getText().matches(regex)) {
						lblErrorHoTen.setText("Họ tên không hợp lệ");
					} else {
						lblErrorHoTen.setText("");
					}
				} else {
					lblErrorHoTen.setText("Chưa nhập Họ tên");
				}
			}
		});
		cbxGioiTinh.valueProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				if (newVal.equals("Nam")) {
					try {
						File file = new File("src/img/man.png");
						FileInputStream inputStream = new FileInputStream(file);
						Image image = new Image(inputStream);
						imgAnhDaiDien.setImage(image);
					} catch (Exception e) {
					}
				} else {
					try {
						File file = new File("src/img/girl.png");
						FileInputStream inputStream = new FileInputStream(file);
						Image image = new Image(inputStream);
						imgAnhDaiDien.setImage(image);
					} catch (Exception e) {
					}
				}
			}
		});

		txtNgaySinh.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtNgaySinh.getText().equals("")) {
					String regex = "\\d{1,2}/\\d{1,2}/\\d{4}$";
					if (!txtNgaySinh.getText().matches(regex)) {
						lblErrorNgaySinh.setText("Ngày chưa hợp lệ");
					} else {
						try {
							String r1 = "\\d{1}/\\d{1}/\\d{4}$";
							String r2 = "\\d{2}/\\d{1}/\\d{4}$";
							String r3 = "\\d{1}/\\d{2}/\\d{4}$";
							String[] temp = txtNgaySinh.getText().split("/");
							String tempNgaySinh = "";
							if (txtNgaySinh.getText().matches(r1)) {
								tempNgaySinh += "0" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgaySinh.getText().matches(r2)) {
								tempNgaySinh += "" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgaySinh.getText().matches(r3)) {
								tempNgaySinh += "0" + temp[0];
								tempNgaySinh += "/" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else {
								tempNgaySinh += "" + temp[0];
								tempNgaySinh += "/" + temp[1];
								tempNgaySinh += "/" + temp[2];
							}
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate localDate = LocalDate.parse(tempNgaySinh, formatter);
							System.out.println(localDate);
							if (!localDate.format(formatter).equals(tempNgaySinh)) {
								lblErrorNgaySinh.setText("Ngày không tồn tại");
							} else if (LocalDate.now().getYear() - localDate.getYear() < 18) {
								lblErrorNgaySinh.setText("Chưa đủ 18 tuổi");
							} else {
								lblErrorNgaySinh.setText("");
							}
						} catch (Exception e) {
							lblErrorNgaySinh.setText("Ngày không tồn tại");
						}
					}
				} else {
					lblErrorNgaySinh.setText("Chưa nhập Ngày sinh");
				}
			}
		});

		txtEmail.fontProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				if (!txtEmail.getText().equals("")) {
					String regex = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
					if (danhSachEmailNV.contains(txtEmail.getText()) || danhSachEmailKH.contains(txtEmail.getText())) {
						lblErrorEmail.setText("Email '" + txtEmail.getText() + "' đã được sử dụng");
					} else if (!txtEmail.getText().matches(regex)) {
						lblErrorEmail.setText("Địa chỉ email không hợp lệ");
					} else {
						lblErrorEmail.setText("");
					}
				} else {
					lblErrorEmail.setText("Chưa nhập Email");
				}
			}
		});

		txtCMND.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtCMND.getText().equals("")) {
					String regex = "\\d{8}";
					String regex1 = "\\d{12}";
					if (!txtCMND.getText().matches(regex) && !txtEmail.getText().matches(regex1)) {
						lblErrorCMND.setText("CMND không hợp lệ");
					} else if (danhSachEmailNV.contains(txtCMND.getText()) || danhSachEmailKH.contains(txtCMND.getText())) {
						lblErrorEmail.setText("Email '" + newVal + "' đã được sử dụng");
					} else {
						lblErrorCMND.setText("");
					}
				} else {
					lblErrorCMND.setText("Chưa nhập CMND");
				}
			}
		});

		txtSoDienThoai.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtSoDienThoai.getText().equals("")) {
					String regex = "\\d{10}";
					String regex1 = "\\d{11}";
					if (danhSachSDTNV.contains(txtSoDienThoai.getText()) == true
							|| danhSachSDTKH.contains(txtSoDienThoai.getText()) == true) {
						lblErrorSoDienThoai.setText("SĐT '" + txtSoDienThoai.getText() + "' đã được sử dụng");
					} else if (!txtSoDienThoai.getText().matches(regex) && !txtSoDienThoai.getText().matches(regex1)) {
						lblErrorSoDienThoai.setText("Số điện thoại không hợp lệ");
					} else {
						lblErrorSoDienThoai.setText("");
					}
				} else {
					lblErrorSoDienThoai.setText("Chưa nhập Số điện thoại");
				}
			}
		});

		txtDiaChi.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtDiaChi.getText().equals("")) {
					lblErrorDiaChi.setText("");
				} else {
					lblErrorDiaChi.setText("Chưa nhập địa chỉ");
				}

			}
		});

		btnCapNhat.disableProperty().bind(txtHoTen.textProperty().isEqualTo(khachHang.getHoTenKH())
				.and(cbxGioiTinh.valueProperty().isEqualTo(khachHang.getGioiTinh()))
				.and(txtNgaySinh.textProperty()
						.isEqualTo(khachHang.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
				.and(txtCMND.textProperty().isEqualTo(khachHang.getCmnd()))
				.and(txtEmail.textProperty().isEqualTo(khachHang.getEmail()))
				.and(txtSoDienThoai.textProperty().isEqualTo(khachHang.getSoDienThoai()))
				.and(txtDiaChi.textProperty().isEqualTo(khachHang.getDiaChi()))
				.or(lblErrorHoTen.textProperty().isNotEmpty()).or(lblErrorGioiTinh.textProperty().isNotEmpty())
				.or(lblErrorNgaySinh.textProperty().isNotEmpty()).or(lblErrorCMND.textProperty().isNotEmpty())
				.or(lblErrorEmail.textProperty().isNotEmpty()).or(lblErrorSoDienThoai.textProperty().isNotEmpty())
				.or(lblErrorDiaChi.textProperty().isNotEmpty()));
	}

	@FXML
	private void handlButtonAction(MouseEvent e) {
		if (e.getSource() == btnClose) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Do you want to exit?");
			alert.setContentText("Are you sure?");

			ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
			ButtonType noBtn = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if (alert.showAndWait().get() == yesBtn) {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			} else
				alert.close();
		} else if (e.getSource() == btnCapNhat) {
			if (capNhatKhachHang() == true) {
				this.flag = true;
				alert(AlertType.INFORMATION, "SUCCESS", "Cập nhật thành công", "");
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
		} else if (e.getSource() == btnXoa) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Do you want to delete?");
			alert.setContentText("Are you sure?");

			ButtonType yesBtn = new ButtonType("Yes", ButtonData.YES);
			ButtonType noBtn = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if (alert.showAndWait().get() == yesBtn) {
				if (xoaKhachHang() > 0) {
					this.flag = true;
					alert(AlertType.INFORMATION, "SUCCESS", "Xoá thông tin Khách hàng thành công", "");
					Node node = (Node) e.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();
				}
			} else
				alert.close();
		}
	}

	private void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private int xoaKhachHang() {
		return 0;
	}

	private boolean capNhatKhachHang() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate localDate = LocalDate.parse(txtNgaySinh.getText(), formatter);
			KhachHang newKhachHang = new KhachHang(khachHang.getMaKH(), txtHoTen.getText(), cbxGioiTinh.getValue(),
					localDate, txtCMND.getText(), txtEmail.getText(), txtSoDienThoai.getText(), txtDiaChi.getText());
			Services services = new Services();
			KhachHangServices khachHangServices = services.getKhachHangServices();
			if (khachHangServices.suaKhachHang(newKhachHang) == true) {
				this.khachHang = newKhachHang;
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public boolean getResult() {
		return flag;
	}

}
