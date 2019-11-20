package control;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.skin.TextFieldSkin;

import application.Services;
import entities.KhachHang;
import entities.NhanVien;
import entities.UserPassword;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.KhachHangServices;
import services.NhanVienServices;

public class ThemKhachHangControl implements Initializable{

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
	@FXML private JFXPasswordField txtMatKhau;
	@FXML private Label lblErrorMatKhau;
	@FXML private JFXCheckBox chkXemPassword;
	
	private boolean flag = false;
	
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
		
		txtMatKhau.textProperty().addListener((val, oldVal, newVal) -> {
			try {
				if(!newVal.equals("")) {
					if(!txtMatKhau.getText().equals("")) {
						if(!txtMatKhau.getText().matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])).{6,16}")) {
							lblErrorMatKhau.setText("Mật khẩu dài từ 6 - 16 kí tự, gồm chứ thường, chữ số,chữ in hoa");
						} else {
							lblErrorMatKhau.setText("");
						}
					} else {
						lblErrorMatKhau.setText("Chưa nhập mật khẩu");
					}
				}
			} catch (Exception e) {
			}
		});
		
        BooleanProperty showPassword = new SimpleBooleanProperty() {
            @Override
            protected void invalidated() {
                // force maskText to be called
                String txt = txtMatKhau.getText();
                txtMatKhau.setText(null);
                txtMatKhau.setText(txt);
            }
        };

        txtMatKhau.setSkin(new TextFieldSkin(txtMatKhau) {
            @Override
            protected String maskText(String txt) {
                if (showPassword.get()) {
                    return txt;
                }
                return super.maskText(txt);
            }
        });
        showPassword.bind(chkXemPassword.selectedProperty());
        
		btnThem.disableProperty().bind(lblErrorHoTen.textProperty().isNotEmpty()
				.or(cbxGioiTinh.valueProperty().isNull())
				.or(lblErrorNgaySinh.textProperty().isNotEmpty())
				.or(lblErrorCMND.textProperty().isNotEmpty())
				.or(lblErrorEmail.textProperty().isNotEmpty())
				.or(lblErrorSoDienThoai.textProperty().isNotEmpty())
				.or(lblErrorDiaChi.textProperty().isNotEmpty())
				.or(lblErrorMatKhau.textProperty().isNotEmpty())
				.or(txtHoTen.textProperty().isEmpty())
				.or(txtNgaySinh.textProperty().isEmpty())
				.or(txtCMND.textProperty().isEmpty())
				.or(txtEmail.textProperty().isEmpty())
				.or(txtSoDienThoai.textProperty().isEmpty())
				.or(txtDiaChi.textProperty().isEmpty())
				.or(txtMatKhau.textProperty().isEmpty()));
		
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
			if(themKhachHang() == true) {
				this.flag = true;
				alert(AlertType.INFORMATION, "SUCCESS", "Thêm Khách hàng thành công", null);
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}

		}
	}
	
	private boolean themKhachHang() {
		try {
			KhachHang newKhachHang = new KhachHang(null, txtHoTen.getText(), cbxGioiTinh.getValue(), LocalDate.parse(txtNgaySinh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), txtCMND.getText(), txtEmail.getText(), txtSoDienThoai.getText(), txtDiaChi.getText());
			UserPassword userPassword = new UserPassword(null, txtMatKhau.getText());
			Services services = new Services();
			KhachHangServices khachHangServices = services.getKhachHangServices();
			return khachHangServices.themKhachHang(newKhachHang, userPassword);
		} catch (Exception e) {
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
	
	public boolean getResult() {
		return flag;
	}
}
