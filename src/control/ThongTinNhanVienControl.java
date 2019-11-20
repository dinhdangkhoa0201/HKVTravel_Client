package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.NhanVien;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.KhachHangServices;
import services.NhanVienServices;

public class ThongTinNhanVienControl implements Initializable {
	@FXML
	private JFXButton btnCapNhat;
	@FXML
	private JFXButton btnXoa;
	@FXML
	private JFXButton btnClose;

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
	private Label lblErrorSDT;
	@FXML
	private ImageView imgAnhDaiDien;
	@FXML
	private JFXButton btnAnhDaiDien;

	private boolean flag;
	private NhanVien nhanVien;
	final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private List<String> danhSachEmailKH;
	private List<String> danhSachEmailNV;

	private List<String> danhSachSDTKH;
	private List<String> danhSachSDTNV;

	private List<String> danhSachCMNDKH;
	private List<String> danhSachCMNDNV;

	private FileChooser fileChooser;
	private File fileAnh;
	private byte[] byteAnh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxGioiTinh.getItems().add("Nam");
		cbxGioiTinh.getItems().add("Nữ");
		fileChooser = new FileChooser();
	}

	public void setValues(NhanVien nhanVien, NhanVien quanLy) {
		this.nhanVien = nhanVien;

		txtHoTen.setText(nhanVien.getHoTen());
		if (!nhanVien.getGioiTinh().equalsIgnoreCase(""))
			cbxGioiTinh.setValue(nhanVien.getGioiTinh());
		txtNgaySinh.setText(nhanVien.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtCMND.setText(nhanVien.getCmnd());
		txtNgayVaoLam.setText(nhanVien.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtEmail.setText(nhanVien.getEmail());
		txtDiaChi.setText(nhanVien.getDiaChi());
		txtSoDienThoai.setText(nhanVien.getSoDienThoai());

		if (nhanVien.getAnh() != null) {
			try {
				System.out.println("Co Anh");
				byteAnh = nhanVien.getAnh();
				InputStream inputStream = new ByteArrayInputStream(byteAnh);
				imgAnhDaiDien.setImage(new Image(inputStream));
			} catch (Exception e) {
			}

		} else {
			if (cbxGioiTinh.getValue().equals("Nam")) {
				try {
					fileAnh = new File("src/img/man.png");
					byteAnh = Files.readAllBytes(fileAnh.toPath());
					InputStream inputStream = new ByteArrayInputStream(byteAnh);

					Image image = new Image(inputStream);
					imgAnhDaiDien.setImage(image);
				} catch (Exception e) {
				}

			} else {
				try {
					fileAnh = new File("src/img/girl.png");
					byteAnh = Files.readAllBytes(fileAnh.toPath());
					InputStream inputStream = new ByteArrayInputStream(byteAnh);

					Image image = new Image(inputStream);
					imgAnhDaiDien.setImage(image);
				} catch (Exception e) {
				}

			}
		}
		SimpleStringProperty propertyMaNV = new SimpleStringProperty(nhanVien.getMaNV());
		SimpleStringProperty propertyMaQL = new SimpleStringProperty(quanLy.getMaNV());
		btnXoa.disableProperty().bind(propertyMaNV.isEqualTo(propertyMaQL));
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
		}

		if (danhSachEmailKH.contains(nhanVien.getEmail())) {
			danhSachEmailKH.remove(nhanVien.getEmail());
		} else if (danhSachEmailNV.contains(nhanVien.getEmail())) {
			danhSachEmailNV.remove(nhanVien.getEmail());
		}

		if (danhSachSDTKH.contains(nhanVien.getSoDienThoai())) {
			danhSachSDTKH.remove(nhanVien.getSoDienThoai());
		} else if (danhSachSDTNV.contains(nhanVien.getSoDienThoai())) {
			danhSachSDTNV.remove(nhanVien.getSoDienThoai());
		}

		if (danhSachCMNDKH.contains(nhanVien.getCmnd())) {
			danhSachCMNDKH.remove(nhanVien.getCmnd());
		} else if (danhSachCMNDNV.contains(nhanVien.getCmnd())) {
			danhSachCMNDNV.remove(nhanVien.getCmnd());
		}

		init();
	}

	private void init() {
		txtHoTen.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				String regex = "\\d+";
				if (newVal.matches(regex)) {
					lblErrorHoTen.setText("Họ tên không hợp lệ");
				} else {
					lblErrorHoTen.setText("");
				}
			} else {
				lblErrorHoTen.setText("Chưa nhập Họ tên");
			}
		});

		cbxGioiTinh.valueProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				System.out.println("byte anh : " + byteAnh);
				if (byteAnh != null) {
					try {
						File fileMan = new File("src/img/man.png");
						byte[] byteMan = Files.readAllBytes(fileMan.toPath());
						System.out.println("Man " + Arrays.equals(byteAnh, byteMan));

						File fileWoman = new File("src/img/girl.png");
						byte[] byteWoman = Files.readAllBytes(fileWoman.toPath());
						System.out.println("Women " + Arrays.equals(byteAnh, byteWoman));

						System.out.println("both : " + Arrays.equals(byteMan, byteWoman));

						if (Arrays.equals(byteAnh, byteMan) == true || Arrays.equals(byteAnh, byteWoman)) {
							if (cbxGioiTinh.getValue().equals("Nam")) {
								try {
									fileAnh = new File("src/img/man.png");
									byteAnh = Files.readAllBytes(fileAnh.toPath());
									InputStream inputStream = new ByteArrayInputStream(byteAnh);

									Image image = new Image(inputStream);
									imgAnhDaiDien.setImage(image);
								} catch (Exception e) {
									e.printStackTrace();
								}

							} else {
								try {
									fileAnh = new File("src/img/girl.png");
									byteAnh = Files.readAllBytes(fileAnh.toPath());
									InputStream inputStream = new ByteArrayInputStream(byteAnh);

									Image image = new Image(inputStream);
									imgAnhDaiDien.setImage(image);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		txtNgaySinh.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
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
				lblErrorNgaySinh.setText("");
			}
		});

		txtEmail.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				String regex = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
				if (danhSachEmailNV.contains(newVal) || danhSachEmailKH.contains(newVal)) {
					lblErrorEmail.setText("Email '" + newVal + "' đã được sử dụng");
				} else if (!newVal.matches(regex)) {
					lblErrorEmail.setText("Địa chỉ email không hợp lệ");
				} else {
					lblErrorEmail.setText("");
				}
			} else {
				lblErrorEmail.setText("");
			}
		});

		txtCMND.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				String regex = "\\d{8}";
				String regex1 = "\\d{12}";
				if (!newVal.matches(regex) && !newVal.matches(regex1)) {
					lblErrorCMND.setText("CMND không hợp lệ");
				} else if (danhSachCMNDKH.contains(txtCMND.getText()) || danhSachCMNDNV.contains(txtCMND.getText())) {
					lblErrorCMND.setText("CMND đã tồn tại");
				} else {
					lblErrorCMND.setText("");
				}
			} else {
				lblErrorCMND.setText("");
			}
		});

		txtSoDienThoai.textProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal.equals("")) {
				if (!txtSoDienThoai.getText().equals("")) {
					String regex = "\\d{10}";
					String regex1 = "\\d{11}";
					if (danhSachSDTNV.contains(txtSoDienThoai.getText()) == true
							|| danhSachSDTKH.contains(txtSoDienThoai.getText()) == true) {
						lblErrorSDT.setText("SĐT '" + txtSoDienThoai.getText() + "' đã được sử dụng");
					} else if (!txtSoDienThoai.getText().matches(regex) && !txtSoDienThoai.getText().matches(regex1)) {
						lblErrorSDT.setText("Số điện thoại không hợp lệ");
					} else {
						lblErrorSDT.setText("");
					}
				}
			} else {
				lblErrorSDT.setText("Chưa nhập Số điện thoại");
			}
		});

		txtDiaChi.textProperty().addListener((val, oldVal, newVal) -> {
			if (newVal.equals("")) {
				lblErrorDiaChi.setText("Chưa nhập địa chỉ");
			} else {
				lblErrorDiaChi.setText("");
			}
		});

		btnCapNhat.disableProperty()
				.bind(txtHoTen.textProperty().isEqualTo(nhanVien.getHoTen())
						.and(cbxGioiTinh.valueProperty().isEqualTo(nhanVien.getGioiTinh()))
						.and(txtNgaySinh.textProperty()
								.isEqualTo(nhanVien.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
						.and(txtCMND.textProperty().isEqualTo(nhanVien.getCmnd()))
						.and(txtEmail.textProperty().isEqualTo(nhanVien.getEmail()))
						.and(txtSoDienThoai.textProperty().isEqualTo(nhanVien.getSoDienThoai()))
						.and(txtDiaChi.textProperty().isEqualTo(nhanVien.getDiaChi()))
						.or(lblErrorHoTen.textProperty().isNotEmpty()).or(lblErrorGioiTinh.textProperty().isNotEmpty())
						.or(lblErrorNgaySinh.textProperty().isNotEmpty()).or(lblErrorCMND.textProperty().isNotEmpty())
						.or(lblErrorEmail.textProperty().isNotEmpty()).or(lblErrorSDT.textProperty().isNotEmpty())
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
				this.flag = true;
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			} else
				alert.close();
		} else if (e.getSource() == btnCapNhat) {
			if (capNhatNhanVien() == true) {
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
				if (xoaNhanVien() == true) {
					this.flag = true;
					alert(AlertType.INFORMATION, "SUCCESS", "Xoá Nhân Viên thành công", "");
					Node node = (Node) e.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					stage.close();
				}
			} else
				alert.close();
		} else if (e.getSource() == btnAnhDaiDien) {
			try {
				byte[] anhDaiDien = themAnhDaiDien();
				InputStream inputStream = new ByteArrayInputStream(anhDaiDien);
				Image image = new Image(inputStream);
				imgAnhDaiDien.setImage(image);

				Services services = new Services();
				NhanVienServices nhanVienServices = services.getNhanVienServices();
				if (nhanVienServices.capNhatAnhDaiDien(nhanVien.getMaNV(), anhDaiDien) == true) {
					alert(AlertType.INFORMATION, "Success", "Đổi ảnh đại diện thành công", null);
					flag = true;
				} else {
					System.out.println("false");
				}
			} catch (Exception e2) {
			}
		}
	}

	private byte[] themAnhDaiDien() {
		byte[] bytesAnh = null;
		try {
			File file = fileChooser.showOpenDialog(null);
			bytesAnh = Files.readAllBytes(file.toPath());

		} catch (Exception e) {
		}
		return bytesAnh;
	}

	private boolean xoaNhanVien() {
		return false;
	}

	private boolean capNhatNhanVien() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate localDate = LocalDate.parse(txtNgaySinh.getText(), formatter);
			NhanVien newNhanVien = new NhanVien(nhanVien.getMaNV(), txtHoTen.getText(),
					cbxGioiTinh.getSelectionModel().getSelectedItem(), localDate, txtCMND.getText(),
					nhanVien.getNgayVaoLam(), txtDiaChi.getText(), nhanVien.getEmail(), txtSoDienThoai.getText(),
					nhanVien.getChucVu(), byteAnh);
			System.out.println("new " + newNhanVien);
			Services services = new Services();
			NhanVienServices nhanVienServices = services.getNhanVienServices();
			if (nhanVienServices.suaNhanVien(newNhanVien) == true) {
				this.nhanVien = newNhanVien;
				return true;
			}
		} catch (Exception e) {
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
