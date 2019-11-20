package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.HuongDanVien;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.HuongDanVienServices;
import services.KhachHangServices;
import services.NhanVienServices;

public class ThemHuongDanVienControl implements Initializable{
	@FXML JFXButton btnClose;
	@FXML JFXButton btnThem;

	@FXML private JFXTextField txtHoTen;
	@FXML private Label lblErrorHoTen;
	@FXML private JFXComboBox<String> cbxGioiTinh;
	@FXML private Label lblErrorGioiTinh;
	@FXML private JFXTextField txtNgaySinh;
	@FXML private Label lblErrorNgaySinh;
	@FXML private JFXTextField txtCMND;
	@FXML private Label lblErrorCMND;
	@FXML private JFXTextField txtEmail;
	@FXML private Label lblErrorEmail;
	
	@FXML private JFXTextField txtDiaChi;
	@FXML private Label lblErrorDiaChi;
	@FXML private JFXTextField txtSoDienThoai;
	@FXML private Label lblErrorSoDienThoai;
	
	@FXML private ImageView imgAnhDaiDien;
	@FXML private JFXButton btnAnhDaiDien;
	
	private FileChooser fileChooser;
	private File fileAnh;
	private byte[] byteAnh;
	
	private List<String> danhSachEmailKH;
	private List<String> danhSachEmailNV;

	private List<String> danhSachSDTKH;
	private List<String> danhSachSDTNV;
	
	private List<String> danhSachCMNDKH;
	private List<String> danhSachCMNDNV;

	private boolean flag = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileChooser = new FileChooser();
		cbxGioiTinh.getItems().add("Nam");
		cbxGioiTinh.getItems().add("Nữ");
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
		
		txtHoTen.focusedProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal) {
				if(!txtHoTen.getText().equals("")) {
					String regex = "\\d+";
					if(txtHoTen.getText().matches(regex)) {
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
			if(!newVal.equals("")) {
				if(byteAnh == null) {
					if(cbxGioiTinh.getValue().equals("Nam")) {
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
			}
		});
		
		cbxGioiTinh.focusedProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal) {
				cbxGioiTinh.validate();
			}
		});
		
		txtNgaySinh.focusedProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal) {
				if(!txtNgaySinh.getText().equals("")) {
					String regex = "\\d{1,2}/\\d{1,2}/\\d{4}$";
					if(!txtNgaySinh.getText().matches(regex)) {
						lblErrorNgaySinh.setText("Ngày chưa hợp lệ");
					} 
					else {
						try {
							String r1 = "\\d{1}/\\d{1}/\\d{4}$";
							String r2 = "\\d{2}/\\d{1}/\\d{4}$";
							String r3 = "\\d{1}/\\d{2}/\\d{4}$";
							String[] temp = txtNgaySinh.getText().split("/");
							String tempNgaySinh = "";
							if(txtNgaySinh.getText().matches(r1)) {
								tempNgaySinh += "0"+temp[0];
								tempNgaySinh += "/0"+temp[1];
								tempNgaySinh += "/"+temp[2];
							} else if(txtNgaySinh.getText().matches(r2)) {
								tempNgaySinh += ""+temp[0];
								tempNgaySinh += "/0"+temp[1];
								tempNgaySinh += "/"+temp[2];
							} else if(txtNgaySinh.getText().matches(r3)) {
								tempNgaySinh += "0"+temp[0];
								tempNgaySinh += "/"+temp[1];
								tempNgaySinh += "/"+temp[2];
							} else {
								tempNgaySinh += ""+temp[0];
								tempNgaySinh += "/"+temp[1];
								tempNgaySinh += "/"+temp[2];
							}
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate localDate = LocalDate.parse(tempNgaySinh, formatter);
							System.out.println(localDate);
							if(!localDate.format(formatter).equals(tempNgaySinh)) {
								lblErrorNgaySinh.setText("Ngày không tồn tại");
							} else if(LocalDate.now().getYear() - localDate.getYear() < 18) {
								lblErrorNgaySinh.setText("Chưa đủ 18 tuổi");
							}
							else {
								lblErrorNgaySinh.setText("");
								txtNgaySinh.setText(tempNgaySinh);
							}
						} catch (Exception e) {
							lblErrorNgaySinh.setText("Ngày không tồn tại");
						}
					}
				} else {
					lblErrorNgaySinh.setText("Chưa nhập ngày sinh");
				}
			}
		});
		
		txtCMND.focusedProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal) {
				if(!txtCMND.getText().equals("")) {
					String regex = "\\d{9}";
					String regex1 = "\\d{12}";
					if(!txtCMND.getText().matches(regex) && !txtCMND.getText().matches(regex1)) {
						lblErrorCMND.setText("CMND không hợp lệ");
					} else if (danhSachCMNDKH.contains(txtCMND.getText()) || danhSachCMNDNV.contains(txtCMND.getText())) {
						lblErrorCMND.setText("CMND đã tồn tại");
					}
					else {
						lblErrorCMND.setText("");
					} 
				} else {
					lblErrorCMND.setText("Chưa nhập CMND");
				}
			}
		});
		
		txtEmail.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if (!txtEmail.getText().equals("")) {
					if (danhSachEmailNV.contains(txtEmail.getText()) || danhSachEmailKH.contains(txtEmail.getText())) {
						lblErrorEmail.setText("Email '" + txtEmail.getText() + "' đã được sử dụng");
					} else {
						String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
						String email = txtEmail.getText();
						if (email.matches(emailPattern)) {
							lblErrorEmail.setText("");
						} else {
							lblErrorEmail.setText("Email không hợp lệ");
						}

					}
				} else if (txtEmail.getText().equals("")) {
					lblErrorEmail.setText("Chưa nhập Email");
				}
			}
		});
		
		txtSoDienThoai.focusedProperty().addListener((val, oldVal, newVal) -> {
			if (!newVal) {
				if(!txtSoDienThoai.getText().equals("")) {
					if (danhSachSDTNV.contains(txtSoDienThoai.getText()) || danhSachSDTKH.contains(txtSoDienThoai.getText())) {
						lblErrorSoDienThoai.setText("SĐT '" + txtSoDienThoai.getText() + "' đã được sử dụng");
					} else if(!txtSoDienThoai.getText().matches("\\d{10}")) {
						lblErrorSoDienThoai.setText("Số điện thoại không hợp lệ");
					}
					else {
						lblErrorSoDienThoai.setText("");
					}
				} else {
					lblErrorSoDienThoai.setText("Chưa nhập Số điện thoại");
				}
			}
		});
		
		txtDiaChi.focusedProperty().addListener((o, oldVal, newVal) -> {
			if(!newVal) {
				if(txtDiaChi.getText().equals("")) {
					lblErrorDiaChi.setText("Chưa nhập địa chỉ");
				}else {
					lblErrorDiaChi.setText("");
				}
			} 
		});
		
		
		btnThem.disableProperty().bind(lblErrorHoTen.textProperty().isNotEmpty()
				.or(cbxGioiTinh.valueProperty().isNull())
				.or(lblErrorNgaySinh.textProperty().isNotEmpty())
				.or(lblErrorCMND.textProperty().isNotEmpty())
				.or(lblErrorEmail.textProperty().isNotEmpty())
				.or(lblErrorSoDienThoai.textProperty().isNotEmpty())
				.or(lblErrorDiaChi.textProperty().isNotEmpty())
				.or(txtHoTen.textProperty().isEmpty())
				.or(txtNgaySinh.textProperty().isEmpty())
				.or(txtCMND.textProperty().isEmpty())
				.or(txtEmail.textProperty().isEmpty())
				.or(txtSoDienThoai.textProperty().isEmpty())
				.or(txtDiaChi.textProperty().isEmpty()));
		
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
		else if(e.getSource() == btnThem) {
			if(themHuongDanVien() == true) {
				this.flag = true;
				alert(AlertType.INFORMATION, "SUCCESS", "Thêm Hướng dẫn viên thành công", null);
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}

		} else if(e.getSource() == btnAnhDaiDien) {
			try {
				byteAnh = themAnhDaiDien();
				InputStream inputStream = new ByteArrayInputStream(byteAnh);
				Image image = new Image(inputStream);
				imgAnhDaiDien.setImage(image);
			} catch (Exception e2) {
			}
		}
	}
	private byte[] themAnhDaiDien() {
		byte[] bytseAnh = null;
		try {
			File file = fileChooser.showOpenDialog(null);
			bytseAnh = Files.readAllBytes(file.toPath());
		} catch (Exception e) {
		}
		return bytseAnh;
	}
	
	
	
	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private boolean themHuongDanVien() {
		try {
			HuongDanVien huongDanVien = new HuongDanVien(null, txtHoTen.getText(), cbxGioiTinh.getValue(), LocalDate.parse(txtNgaySinh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCMND.getText(), txtEmail.getText(), txtSoDienThoai.getText(), txtDiaChi.getText(), false, byteAnh);
			Services services = new Services();
			HuongDanVienServices huongDanVienServices = services.getHuongDanVienServices();
			return huongDanVienServices.themHuongDanVien(huongDanVien);
		} catch (Exception e) {
		}
		return false;
	}

	public boolean getResult() {
		return flag;
	}
	
}
