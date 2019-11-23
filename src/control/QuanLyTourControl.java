package control;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.Services;
import entities.Tour;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import services.TourServices;

public class QuanLyTourControl implements Initializable {
	@FXML
	private JFXButton btnBack;
	@FXML
	private JFXButton btnClose;
	@FXML
	private JFXButton btnThem;
	@FXML
	private JFXButton btnReload;
	@FXML
	private TableView<Tour> tableTour;
	@FXML
	private TableColumn<Tour, String> col_matour;
	@FXML
	private TableColumn<Tour, String> col_tentour;
	@FXML
	private TableColumn<Tour, String> col_noidi;
	@FXML
	private TableColumn<Tour, String> col_noiden;
	@FXML
	private TableColumn<Tour, String> col_ngaykhoihanh;
	@FXML
	private TableColumn<Tour, String> col_ngayketthuc;
	@FXML
	private TableColumn<Tour, String> col_giokhoihanh;
	@FXML
	private TableColumn<Tour, String> col_phuongtien;
	@FXML
	private TableColumn<Tour, Boolean> col_hienthi;
	@FXML
	private TableColumn<Tour, String> col_gia;

	@FXML
	private ScrollPane scroll;
	@FXML
	private JFXTextField txtSearchTenTour;
	@FXML
	private JFXTextField txtSearchNoiDi;
	@FXML
	private JFXTextField txtSearchNoiDen;
	@FXML
	private JFXTextField txtSearchNgayKhoiHanh;

	private ObservableList<Tour> data;
	private FilteredList<Tour> filteredList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// JFXScrollPane.smoothScrolling((ScrollPane) scroll.getChildren().get(0));
		data = FXCollections.observableArrayList();
		filteredList = new FilteredList<>(data);
		txtSearchTenTour.textProperty().addListener((o, oldVal, newVal) -> {
			filteredList.setPredicate(tour -> {
				if (newVal.equals(""))
					return true;
				String lowcase = newVal.toLowerCase();
				if (tour.getTenTour().toLowerCase().contains(lowcase))
					return true;
				return false;
			});
		});

		txtSearchNoiDi.textProperty().addListener((o, oldVal, newVal) -> {
			filteredList.setPredicate(tour -> {
				if (newVal.equals(""))
					return true;
				String lowcase = newVal.toLowerCase();
				if (tour.getNoiDi().toLowerCase().contains(lowcase))
					return true;
				return false;
			});
		});

		txtSearchNoiDen.textProperty().addListener((o, oldVal, newVal) -> {
			filteredList.setPredicate(tour -> {
				if (newVal.equals(""))
					return true;
				String lowcase = newVal.toLowerCase();
				if (tour.getNoiDen().toLowerCase().contains(lowcase))
					return true;
				return false;
			});
		});

		txtSearchNgayKhoiHanh.textProperty().addListener((o, oldVal, newVal) -> {
			filteredList.setPredicate(tour -> {
				if (newVal == null)
					return true;
				String lowcase = newVal.toString().toLowerCase();
				if (tour.getNgayKhoiHanh().toString().toLowerCase().contains(lowcase))
					return true;
				return false;
			});
		});
		setCellTable();
		init();
		loadDataFromDatabase();
	}

	private void setCellTable() {
		scroll = new ScrollPane();
		data = FXCollections.observableArrayList();
		col_matour.setCellValueFactory(new PropertyValueFactory<>("maTour"));
		col_tentour.setCellValueFactory(new PropertyValueFactory<>("tenTour"));
		col_noidi.setCellValueFactory(new PropertyValueFactory<>("noiDi"));
		col_noiden.setCellValueFactory(new PropertyValueFactory<>("noiDen"));
		col_ngaykhoihanh.setCellValueFactory(new PropertyValueFactory<>("ngayKhoiHanh"));
		col_ngayketthuc.setCellValueFactory(new PropertyValueFactory<>("ngayKetThuc"));
		col_giokhoihanh.setCellValueFactory(new PropertyValueFactory<>("gioiKhoiHanh"));
		col_phuongtien.setCellValueFactory(new PropertyValueFactory<>("phuongTien"));
		col_hienthi.setCellValueFactory(new PropertyValueFactory<>("hienThi"));
		col_gia.setCellValueFactory(new PropertyValueFactory<>("giaVe"));
		tableTour.setItems(data);
	}

	private void init() {
		col_hienthi.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tour, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(CellDataFeatures<Tour, Boolean> param) {
						// TODO Auto-generated method stub
						Tour tour = param.getValue();
						SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(tour.isHienThi());
						return booleanProperty;
					}
				});
		col_hienthi.setCellFactory(new Callback<TableColumn<Tour, Boolean>, TableCell<Tour, Boolean>>() {

			@Override
			public TableCell<Tour, Boolean> call(TableColumn<Tour, Boolean> param) {
				// TODO Auto-generated method stub
				CheckBoxTableCell<Tour, Boolean> cell = new CheckBoxTableCell<>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
		col_tentour.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tour, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Tour, String> param) {
						// TODO Auto-generated method stub
						Tour tour = param.getValue();
						SimpleStringProperty stringProperty = new SimpleStringProperty(tour.getTenTour());
						return stringProperty;
					}
				});
		col_noidi.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tour, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Tour, String> param) {
						// TODO Auto-generated method stub
						Tour tour = param.getValue();
						SimpleStringProperty stringProperty = new SimpleStringProperty(tour.getNoiDi());
						return stringProperty;
					}
				});
		col_noiden.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tour, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Tour, String> param) {
						// TODO Auto-generated method stub
						Tour tour = param.getValue();
						SimpleStringProperty stringProperty = new SimpleStringProperty(tour.getNoiDen());
						return stringProperty;
					}
				});
		col_giokhoihanh.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tour, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Tour, String> param) {
						// TODO Auto-generated method stub
						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
						return new SimpleObjectProperty<>(timeFormatter.format(param.getValue().getGioKhoiHanh()));
					}
				});

	}

	private void loadDataFromDatabase() {
		try {
			Services services = new Services();
			TourServices tourServices = services.getTourServices();
			data = FXCollections.observableArrayList(tourServices.danhsachTour());
			System.out.println(data);
			filteredList = new FilteredList<>(data, p -> true);
			tableTour.setItems(filteredList);
			Platform.runLater(() -> tableTour.refresh());
			tableTour.getSelectionModel().clearSelection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleButtonAction(MouseEvent e) {
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
		} else if (e.getSource() == btnBack) {
			try {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();

				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/gui/MenuNhanVien.fxml")));
				stage.setScene(scene);
				stage.show();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnThem) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemTour.fxml"));
				DialogPane dialogPane = new DialogPane();
				dialogPane = fxmlLoader.load();
				ThemTourControl themTourControl = fxmlLoader.getController();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Thêm Khách Hàng");
				alert.setResizable(false);
				alert.setDialogPane(dialogPane);
				alert.initModality(Modality.APPLICATION_MODAL);
				Window window = alert.getDialogPane().getScene().getWindow();
				window.setOnCloseRequest(event -> window.hide());
				alert.showAndWait();
				if (themTourControl.getResult() == true) {
					alert.close();
				}

			} catch (Exception e2) {

				System.out.println(e2.getMessage());
				System.out.println("Button Add");
				e2.printStackTrace();
			}
		} else if (e.getSource() == btnReload) {
			loadDataFromDatabase();
		} else {
			tableTour.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Tour tour = tableTour.getSelectionModel().getSelectedItem();
					if (event.getClickCount() == 2 && tour != null) {

						try {
							// Stage stage = new Stage();
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThemTour.fxml"));
							DialogPane dialogPane = new DialogPane();
							dialogPane = fxmlLoader.load();
							ThemTourControl themTourControl = fxmlLoader.getController();
							themTourControl.setValues(tour);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Thông tin Tour");
							alert.setResizable(false);
							alert.setDialogPane(dialogPane);
							alert.initModality(Modality.APPLICATION_MODAL);
							Window window = alert.getDialogPane().getScene().getWindow();
							window.setOnCloseRequest(e -> window.hide());

							alert.showAndWait();
							if (themTourControl.getResult() == true) {
								loadDataFromDatabase();
							}
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
				}
			});
		}
	}
}
