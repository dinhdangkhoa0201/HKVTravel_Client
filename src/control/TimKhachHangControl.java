package control;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.KhachHang;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import services.KhachHangServices;

public class TimKhachHangControl implements Initializable{
	@FXML private JFXTextField txtTimTen;
	@FXML private JFXTextField txtTimDiaChi;
	@FXML private JFXTextField txtTimSoDienThoai;
	@FXML private JFXCheckBox chkThamGia;
	@FXML private JFXButton btnDatTour;
	@FXML private JFXButton btnSearch;
	@FXML private JFXButton btnReLoad;
	
	@FXML private TableView<KhachHang> tableKhachHang;
	@FXML private TableColumn<KhachHang, String> col_makh;
	@FXML private TableColumn<KhachHang, String> col_hoten;
	@FXML private TableColumn<KhachHang, String> col_gioitinh;
	@FXML private TableColumn<KhachHang, String> col_ngaysinh;
	@FXML private TableColumn<KhachHang, String> col_email;
	@FXML private TableColumn<KhachHang, String> col_dienthoai;
	@FXML private TableColumn<KhachHang, String> col_diachi;
	
	private ObservableList<KhachHang> data;
	private FilteredList<KhachHang> filter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		data = FXCollections.observableArrayList();
		filter = new FilteredList<>(data);
		setCellTable();
		loadDataFromDatabase();
	}
	
	private void setCellTable() {
		col_makh.setCellValueFactory(new PropertyValueFactory<>("maKH"));
		col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
		col_gioitinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		col_ngaysinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
		col_dienthoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
		col_diachi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		
		col_hoten.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KhachHang,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<KhachHang, String> param) {
				// TODO Auto-generated method stub
				KhachHang khachHang = param.getValue();
				SimpleStringProperty stringProperty = new SimpleStringProperty(khachHang.getHoTenKH());
				return stringProperty;
			}
		});
		col_gioitinh.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KhachHang,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<KhachHang, String> param) {
				return new SimpleStringProperty(param.getValue().getGioiTinh());
			}
		});
		col_ngaysinh.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KhachHang,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<KhachHang, String> param) {
				return new SimpleObjectProperty<String>(param.getValue().getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			}
		});
		col_diachi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KhachHang,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<KhachHang, String> param) {
				// TODO Auto-generated method stub
				KhachHang khachHang = param.getValue();
				SimpleStringProperty stringProperty = new SimpleStringProperty(khachHang.getDiaChi());
				return stringProperty;
			}
		});
	}
	
	public void loadDataFromDatabase() {
		try {
			Services services = new Services();
			KhachHangServices khachHangServices = services.getKhachHangServices();
			data = FXCollections.observableArrayList(khachHangServices.danhsachKhachHang());
			filter = new FilteredList<>(data, p -> true);
			tableKhachHang.setItems(filter);
			tableKhachHang.getSelectionModel().clearSelection();
			Platform.runLater(() -> tableKhachHang.refresh());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handlButtonAction(MouseEvent e) {
		if(e.getSource() == btnReLoad) {
			loadDataFromDatabase();
		}
		else if(e.getSource() == btnDatTour) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/KhachHang.fxml"));
				DialogPane dialogPane = new DialogPane();
				dialogPane = loader.load();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Khách Hàng");
				alert.setResizable(false);
				alert.setDialogPane(dialogPane);
				alert.initModality(Modality.APPLICATION_MODAL);
				Window windows =alert.getDialogPane().getScene().getWindow();
				windows.setOnCloseRequest(null);
				alert.showAndWait();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		else {
			tableKhachHang.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					KhachHang khachHang = tableKhachHang.getSelectionModel().getSelectedItem();
					if(event.getClickCount()==2 && khachHang!=null) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinKhachHang.fxml"));
							DialogPane dialogPane = new DialogPane();
							
							dialogPane = fxmlLoader.load();
							ThongTinKhachHangControl thongTinKhachHangControl = fxmlLoader.getController();
							thongTinKhachHangControl.setValues(khachHang);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Thông tin Nhân viên");
							alert.setResizable(false);
							alert.setDialogPane(dialogPane);
							alert.initModality(Modality.APPLICATION_MODAL);
							Window window = alert.getDialogPane().getScene().getWindow();
							window.setOnCloseRequest(e -> window.hide());
							alert.showAndWait();
							if(thongTinKhachHangControl.getResult() == true) {
								loadDataFromDatabase();
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			});
		}
	}

}
