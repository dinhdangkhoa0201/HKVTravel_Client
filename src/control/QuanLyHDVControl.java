package control;

import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.HuongDanVien;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import services.HuongDanVienServices;

public class QuanLyHDVControl implements Initializable{
	@FXML private JFXButton btnReLoad;
	@FXML private JFXButton btnThem;
	@FXML private JFXButton btnTim;
	@FXML private JFXTextField txtTim;

	@FXML private TableView<HuongDanVien> tableHuongDanVien;
	@FXML private TableColumn<HuongDanVien, String> col_mahdv;
	@FXML private TableColumn<HuongDanVien, String> col_hoten;
	@FXML private TableColumn<HuongDanVien, String> col_gioitinh;
	@FXML private TableColumn<HuongDanVien, LocalDate> col_ngaysinh;	
	@FXML private TableColumn<HuongDanVien, String> col_CMND;
	@FXML private TableColumn<HuongDanVien, String> col_diachi;
	@FXML private TableColumn<HuongDanVien, String> col_email;
	@FXML private TableColumn<HuongDanVien, String> col_dienthoai;
	@FXML private TableColumn<HuongDanVien, Boolean> col_trangthai;
	private ObservableList<HuongDanVien> data;
	private FilteredList<HuongDanVien> filter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		data = FXCollections.observableArrayList();
		filter = new FilteredList<>(data);
		
		txtTim.textProperty().addListener((observable, oldValu,newValue)->{
			filter.setPredicate(hdv ->{
				if(newValue.equals("")) {
					return true;
				}
				
				else {
					String temp  = Normalizer.normalize(hdv.getHoTenHDV(), Normalizer.Form.NFD);
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
	}

	private void setCellTable() {
		col_mahdv.setCellValueFactory(new PropertyValueFactory<>("maHDV"));
		col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
		col_gioitinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		col_ngaysinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		col_CMND.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
		col_diachi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
		col_dienthoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
		col_trangthai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
		col_hoten.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HuongDanVien,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<HuongDanVien, String> param) {
				// TODO Auto-generated method stub
				HuongDanVien huongDanVien = param.getValue();
				SimpleStringProperty property = new SimpleStringProperty(huongDanVien.getHoTenHDV());
				return property;
			}
		});

		col_trangthai.setCellFactory(new Callback<TableColumn<HuongDanVien,Boolean>, TableCell<HuongDanVien,Boolean>>() {
			
			@Override
			public TableCell<HuongDanVien, Boolean> call(TableColumn<HuongDanVien, Boolean> param) {
				// TODO Auto-generated method stub
				return new TableCell<HuongDanVien, Boolean>(){
					 @Override
	                    protected void updateItem(Boolean item, boolean empty) {
	                        if (!empty) {
	                            int currentIndex = indexProperty()
	                                    .getValue() < 0 ? 0
	                                    : indexProperty().getValue();
	                            Boolean clmStatus = param
	                                    .getTableView().getItems()
	                                    .get(currentIndex).isTrangThai();
	                            if (clmStatus == true) {
	                                setTextFill(Color.RED);
	                                setStyle("-fx-font-weight: bold");
	                                setStyle("-fx-background-color: red");
	                            } else {
	                                setTextFill(Color.GREEN);
	                                setStyle("-fx-font-weight: bold");
	                                setStyle("-fx-background-color: #36f719");
	                            }
	                        }
	                    }
				};
			}
		});
	}

	private void loadDataFromDatabase() {
		try {
			Services services = new Services();
			HuongDanVienServices huongDanVienServices = services.getHuongDanVienServices();
			data = FXCollections.observableArrayList(huongDanVienServices.danhSachHuongDanVien());
			filter = new FilteredList<>(data, p -> true);
			tableHuongDanVien.setItems(filter);
			tableHuongDanVien.getSelectionModel().clearSelection();
			Platform.runLater(() -> tableHuongDanVien.refresh());
		} catch (Exception e) {
		}
	}

	@FXML
	private void handleButtonAction(MouseEvent e) {
		if(e.getSource() == btnReLoad) {
			loadDataFromDatabase();
		}
		else if(e.getSource() == btnThem) {
			themHuongDanVien();
			loadDataFromDatabase();
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
					if(data.get(i).getHoTenHDV().equals(tim)) {
						TableViewSelectionModel<HuongDanVien> model = tableHuongDanVien.getSelectionModel();
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
			tableHuongDanVien.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					HuongDanVien huongDanVien = tableHuongDanVien.getSelectionModel().getSelectedItem();
					if(event.getClickCount()==2 && huongDanVien!=null) {
						System.out.println(huongDanVien);
						try {
							new Stage();
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinHDV.fxml"));
							DialogPane dialogPane = new DialogPane();
							dialogPane = fxmlLoader.load();
							ThongTinHDVControl thongTinNhanVienControl = fxmlLoader.getController();
							thongTinNhanVienControl.setValues(huongDanVien);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Thông tin Nhân viên");
							alert.setResizable(false);
							alert.setDialogPane(dialogPane);
							alert.initModality(Modality.APPLICATION_MODAL);
							Window window = alert.getDialogPane().getScene().getWindow();
							window.setOnCloseRequest(e -> window.hide());

							alert.showAndWait();
							if(thongTinNhanVienControl.getResult() == true) {
								loadDataFromDatabase();
							}
						} catch (Exception e2) {
							// TODO: handle exception
							System.out.println(e2.getMessage());
							e2.printStackTrace();

						}
					}
				}
			});
		}
	}

	private void themHuongDanVien() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemHuongDanVien.fxml"));
			DialogPane dialogPane = new DialogPane();
			dialogPane = fxmlLoader.load();
			ThemHuongDanVienControl themHDVControl = fxmlLoader.getController();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Thêm Nhân Viên");
			alert.setResizable(false);
			alert.setDialogPane(dialogPane);
			alert.initModality(Modality.APPLICATION_MODAL);
			Window window = alert.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());

			alert.showAndWait();
			if(themHDVControl.getResult() == true) {
				loadDataFromDatabase();
			}
		} catch (Exception e2) {
			// TODO: handle exception
			System.out.println(e2.getMessage());
			System.out.println("Button Add");
			e2.printStackTrace();

		}
	}

}
