package control;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.NhanVien;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import services.NhanVienServices;

public class ThongTinCaNhanControl implements Initializable{
	@FXML private JFXTextField txtHoTen;
	@FXML private Label lblErrorHoTen;
	@FXML private JFXTextField txtEmail; 
	@FXML private JFXTextField txtSoDT;
	@FXML private Label lblErrorSoDT;
	@FXML private JFXTextField txtCMND;
	@FXML private Label lblErrorCMND;
	@FXML private JFXComboBox<String> cbxGioiTinh;
	@FXML private JFXTextField txtNgaySinh;
	@FXML private Label lblErrorNgaySinh;
	@FXML private JFXTextField txtDiaChi;
	@FXML private Label lblErrorDiaChi;
	@FXML private Label lblChucVu;

	@FXML private JFXButton btnLuu;

	private boolean flag;
	private NhanVien nhanVien;
	private Object btnClose;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cbxGioiTinh.getItems().add("Nam");
		cbxGioiTinh.getItems().add("Nữ");
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

		init();
	}

	private void init() {
		txtHoTen.textProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal.equals("")) {
				String regex = "\\d+";
				if(newVal.matches(regex)) {
					lblErrorHoTen.setText("Họ tên không hợp lệ");
				} else {
					lblErrorHoTen.setText("");
				}
			} else {
				lblErrorHoTen.setText("Chưa nhập Họ tên");
			}
		});

		txtSoDT.textProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal.equals("")) {
				String regex = "\\d{10}";
				if(!newVal.matches(regex)) {
					lblErrorSoDT.setText("Số điện thoại không hợp lệ");
				} else {
					lblErrorSoDT.setText("");
				}
			} else {
				lblErrorSoDT.setText("Chưa nhập Số điện thoại");
			}
		});

		txtCMND.textProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal.equals("")) {
				String regex = "\\d{8}";
				String regex1 = "\\d{12}";
				if(!newVal.matches(regex) && !newVal.matches(regex1)) {
					lblErrorCMND.setText("CMND không hợp lệ");
				} else {
					lblErrorCMND.setText("");
				} 
			} else {
				lblErrorCMND.setText("");
			}
		});

		txtNgaySinh.textProperty().addListener((val, oldVal, newVal) -> {
			if(!newVal.equals("")) {
				String regex = "\\d{1,2}/\\d{1,2}/\\d{4}$";
				if(!newVal.matches(regex)) {
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
						}
					} catch (Exception e) {
						lblErrorNgaySinh.setText("Ngày không tồn tại");
					}
				}
			}
		});
		
		txtDiaChi.textProperty().addListener((o, oldVal, newVal) -> {
			if(newVal.equals("")) {
				lblErrorDiaChi.setText("Chưa nhập địa chỉ");
			} else {
				lblErrorDiaChi.setText("");
			}
		});

		btnLuu.disableProperty().bind(txtHoTen.textProperty().isEqualTo(nhanVien.getHoTen())
				.and(txtSoDT.textProperty().isEqualTo(nhanVien.getSoDienThoai()))
				.and(txtCMND.textProperty().isEqualTo(nhanVien.getCmnd()))
				.and(cbxGioiTinh.valueProperty().isEqualTo(nhanVien.getGioiTinh()))
				.and(txtNgaySinh.textProperty().isEqualTo(nhanVien.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
				.and(txtDiaChi.textProperty().isEqualTo(nhanVien.getDiaChi()))
				.or(lblErrorHoTen.textProperty().isNotEmpty())
				.or(lblErrorCMND.textProperty().isNotEmpty())
				.or(lblErrorNgaySinh.textProperty().isNotEmpty())
				.or(lblErrorDiaChi.textProperty().isNotEmpty()));
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
				Services services = new Services();
				NhanVienServices nhanVienServices = services.getNhanVienServices();
				try {
					nhanVienServices.dangXuat(nhanVien);
				} catch (RemoteException event) {
					// TODO Auto-generated catch block
					event.printStackTrace();
				}
				stage.close();
			}
			else
				alert.close();
		}
		else if(e.getSource() == btnLuu) {
			if(capNhatNhanVien() == true) {
			}
		}
	}


	private boolean capNhatNhanVien() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");		
		try {
			LocalDate localDate = LocalDate.parse(txtNgaySinh.getText(), formatter);
			NhanVien newNhanVien = new NhanVien(nhanVien.getMaNV(), txtHoTen.getText(), cbxGioiTinh.getSelectionModel().getSelectedItem(), localDate, txtCMND.getText(), nhanVien.getNgayVaoLam(), txtDiaChi.getText(), nhanVien.getEmail(), txtSoDT.getText(), nhanVien.getChucVu());
			System.out.println("new "+newNhanVien);
			Services services = new Services();
			NhanVienServices nhanVienServices = services.getNhanVienServices();
			if(nhanVienServices.suaNhanVien(newNhanVien) == true) {
				this.nhanVien = newNhanVien;
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean getResult() {
		return flag;
	}



	private void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
