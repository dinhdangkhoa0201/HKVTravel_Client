package control;

import java.net.URL;
import java.rmi.RemoteException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.KhachHang;
import entities.NhanVien;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import services.NhanVienServices;

public class QuanLyNhanVienControl implements Initializable{
	@FXML private JFXButton btnReLoad;
	@FXML private JFXButton btnThem;
	@FXML private JFXButton btnTim;
	@FXML private JFXTextField txtTim;

	@FXML private TableView<NhanVien> tableNhanVien;
	@FXML private TableColumn<NhanVien, String> col_manv;
	@FXML private TableColumn<NhanVien, String> col_hoten;
	@FXML private TableColumn<NhanVien, String> col_gioitinh;
	@FXML private TableColumn<NhanVien, LocalDate> col_ngaysinh;	
	@FXML private TableColumn<NhanVien, LocalDate> col_ngayvaolam;
	@FXML private TableColumn<NhanVien, String> col_CMND;
	@FXML private TableColumn<NhanVien, String> col_diachi;
	@FXML private TableColumn<NhanVien, String> col_email;
	@FXML private TableColumn<NhanVien, String> col_dienthoai;


	private NhanVien quanLy;
	private ObservableList<NhanVien> data;
	private FilteredList<NhanVien> filter;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		data = FXCollections.observableArrayList();
		filter = new FilteredList<>(data);
		
		txtTim.textProperty().addListener((observable, oldValu,newValue)->{
			filter.setPredicate(nhanvien ->{
				if(newValue.equals("")) {
					return true;
				}
				
				else {
					String temp  = Normalizer.normalize(nhanvien.getHoTen(), Normalizer.Form.NFD);
					String ten = Normalizer.normalize(newValue, Normalizer.Form.NFD);
					Pattern pattern  = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
					if (pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "").toLowerCase().contains(pattern.matcher(ten).replaceAll("").replaceAll("Đ", "D").replace("đ", "").toLowerCase())) {
						return true;
					}
					return false;
				}
			});
		});
		setCellTable();
		loadDataFromDatabase();
	}
	
	public void setValues(NhanVien quanLy) {
		this.quanLy = quanLy;
	}

	private void setCellTable() {
		col_manv.setCellValueFactory(new PropertyValueFactory<>("maNV"));
		col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
		col_gioitinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		col_ngaysinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		col_ngayvaolam.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
		col_CMND.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
		col_diachi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
		col_dienthoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
		col_hoten.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<NhanVien, String> param) {
				// TODO Auto-generated method stub
				NhanVien nhanVien = param.getValue();
				SimpleStringProperty stringProperty = new SimpleStringProperty(nhanVien.getHoTen());
				return stringProperty;
			}
		});
		col_diachi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<NhanVien, String> param) {
				// TODO Auto-generated method stub
				NhanVien nhanVien = param.getValue();
				SimpleStringProperty stringProperty = new SimpleStringProperty(nhanVien.getDiaChi());
				return stringProperty;
			}
		});
	}

	private void loadDataFromDatabase() {
		try {
			Services services = new Services();
			NhanVienServices nhanVienServices = services.getNhanVienServices();
			data = FXCollections.observableArrayList(nhanVienServices.danhsachNhanVien());
			filter = new FilteredList<>(data, p -> true);
			tableNhanVien.setItems(filter);
			tableNhanVien.getSelectionModel().clearSelection();
			Platform.runLater(() -> tableNhanVien.refresh());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void handlButtonAction(MouseEvent e) {
		if(e.getSource() == btnReLoad) {
			loadDataFromDatabase();
		}
		else if(e.getSource() == btnThem) {
			themNhanVien();
		}
		else if(e.getSource() == btnTim) {
			String tim = txtTim.getText();
			if(tim.equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Chưa nhập tên nhân viên cần tìm");
				alert.showAndWait();
			}
			else {
				int size = data.size();
				boolean k = false;
				for(int i=0; i<size; i++) {
					if(data.get(i).getHoTen().equals(tim)) {
						TableViewSelectionModel<NhanVien> model = tableNhanVien.getSelectionModel();
						model.clearSelection();
						model.select(i);
						k = true;
					}
				}
				if(!k) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Tên nhân viên không tồn tại");
					txtTim.requestFocus();
					alert.showAndWait();
				}
			}
		}
		else {
//			tableNhanVien.setOnMouseClicked(new EventHandler<MouseEvent>() {
//				@Override
//				public void handle(MouseEvent event) {
//					NhanVien nhanvien = tableNhanVien.getSelectionModel().getSelectedItem();
//					if(event.getClickCount()==2 && nhanvien!=null) {
//						System.out.println(nhanvien);
//						try {
//							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinNhanVien.fxml"));
//							DialogPane dialogPane = new DialogPane();
//							dialogPane = fxmlLoader.load();
//							ThongTinNhanVienControl thongTinNhanVienControl = fxmlLoader.getController();	
//							thongTinNhanVienControl.setValues(nhanvien, quanLy);
//							Alert alert = new Alert(AlertType.CONFIRMATION);
//							alert.setTitle("Thông tin Nhân viên");
//							alert.setResizable(false);
//							alert.setDialogPane(dialogPane);
//							alert.initModality(Modality.APPLICATION_MODAL);
//							Window window = alert.getDialogPane().getScene().getWindow();
//							window.setOnCloseRequest(e -> window.hide());
//
//							alert.showAndWait();
//							if(thongTinNhanVienControl.getResult() == true) {
//								loadDataFromDatabase();
//							}
//						} catch (Exception e2) {
//							// TODO: handle exception
//							System.out.println(e2.getMessage());
//							e2.printStackTrace();
//
//						}
//					}
//				}
//			});
		}
	}

	private void themNhanVien() {
//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemNhanVien.fxml"));
//			DialogPane dialogPane = new DialogPane();
//			dialogPane = fxmlLoader.load();
//			ThemNhanVienControl themNhanVienControl = fxmlLoader.getController();
//			Alert alert = new Alert(AlertType.CONFIRMATION);
//			alert.setTitle("Thêm Nhân Viên");
//			alert.setResizable(false);
//			alert.setDialogPane(dialogPane);
//			alert.initModality(Modality.WINDOW_MODAL);
//			Window window = alert.getDialogPane().getScene().getWindow();
//			window.setOnCloseRequest(event -> window.hide());
//
//			alert.showAndWait();
//			if(themNhanVienControl.getResult() == true) {
//				loadDataFromDatabase();
//			}
//		} catch (Exception e2) {
//			// TODO: handle exception
//			System.out.println(e2.getMessage());
//			System.out.println("Button Add");
//			e2.printStackTrace();
//
//		}
	}

}
