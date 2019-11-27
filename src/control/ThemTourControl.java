package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;

import application.Services;
import entities.ChiTietTour;
import entities.HuongDanVien;
import entities.Tour;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import services.ChiTietTourServices;
import services.HuongDanVienServices;
import services.TourServices;

import static javafx.animation.Interpolator.EASE_BOTH;

public class ThemTourControl implements Initializable {

	@FXML
	private JFXButton btnClose;
	@FXML
	private JFXButton btnThemTour;
	@FXML
	private JFXButton btnThemAnh;
	@FXML
	private JFXButton btnThemAnhDaiDien;
	@FXML
	private JFXButton btnReload;

	@FXML
	private JFXButton btnReloadAnhDaiDien;
	@FXML
	private JFXButton btnCapNhat;
	@FXML
	private JFXButton btnDemo;

	@FXML
	private JFXToggleButton btnXacNhanMoTa;
	@FXML
	private JFXToggleButton btnXacNhanCTT;
	@FXML
	private JFXToggleButton btnXacNhanCST;

	@FXML
	private JFXTextField txtTenTour;
	@FXML
	private Label lblErrorTenTour;
	@FXML
	private JFXComboBox<String> cbxNoiDi;
	@FXML
	private Label lblErrorNoiKhoiHanh;
	@FXML
	private JFXComboBox<String> cbxNoiDen;
	@FXML
	private Label lblErrorNoiDen;
	@FXML
	private JFXComboBox<String> cbxPhuongTien;
	@FXML
	private Label lblErrorPhuongTien;
	@FXML
	private JFXComboBox<String> cbxSoLuong;
	@FXML
	private Label lblErrorSoLuong;

	@FXML
	private JFXTextField txtNgayKhoiHanh;
	@FXML
	private Label lblErrorNgayKhoiHanh;
	@FXML
	private JFXTextField txtNgayKetThuc;
	@FXML
	private Label lblErrorNgayKetThuc;
	@FXML
	private JFXTextField txtGioKhoiHanh;
	@FXML
	private Label lblErrorGioKhoiHanh;
	@FXML
	private JFXTextField txtGiaVe;
	@FXML
	private Label lblErrorGiaVe;
	@FXML
	private JFXComboBox<HuongDanVien> cbxHuongDanVien;
	@FXML
	private Label lblErrorHuongDanVien;
	@FXML
	private JFXCheckBox chkHienThi;

	@FXML
	private HTMLEditor editorChuongTrinhTour;
	@FXML
	private HTMLEditor editorChinhSachTour;
	@FXML
	private HTMLEditor editorMoTa;

	@FXML
	private ScrollPane scrollThemAnh;
	@FXML
	private ScrollPane scrollMain;
	@FXML
	private ScrollPane scrollThemAnhDaiDien;

	@FXML
	private JFXMasonryPane masonryDanhSachAnh;
	@FXML
	private JFXMasonryPane masonryAnhDaiDien;
	@FXML
	private TabPane tab;

	private List<String> dataCBXMienBac = Arrays.asList("Hà Nội", "Hạ Long", "Sapa", "Ninh Bình", "Hoà Bình");
	private List<String> dataCBXMienTrung = Arrays.asList("Phan Thiết", "Nha Trang", "Đà Lạt", "Tây Nguyên",
			"Quãng Ngãi", "Hội An - Đà Nẵng", "Huế - Quãng Bình");
	private List<String> dataCBXMienNam = Arrays.asList("Phú Quốc", "Miền Tây - ĐBSCL", "Côn Đảo", "Hồ Tràm");
	private List<String> dataNoiDi = Arrays.asList("TP. Hồ Chí Minh", "Hà Nội");
	private List<String> dataPhuongTien = Arrays.asList("Máy bay", "Xe");

	private File fileAnhDaiDien;
	private Tour tour;
	private ChiTietTour chiTietTour;
	private boolean flag = false;
	private List<HuongDanVien> danhsachHuongDanVien;
	private List<Tour> danhSachTour;

	private FileChooser fileChooser;
	private byte[] byteAnhDaiDien;
	private List<File> danhSachFileAnh;
	private List<String> danhSachAnh;
	private ArrayList<HuongDanVien> tempDanhSachHuongDanVien;
	private List<byte[]> danhSachByteAnh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		danhSachByteAnh = new ArrayList<>();
		danhSachFileAnh = new ArrayList<>();
		fileChooser = new FileChooser();
		cbxNoiDi.getItems().addAll(dataNoiDi);
		cbxNoiDen.getItems().addAll(dataCBXMienBac);
		cbxNoiDen.getItems().addAll(dataCBXMienTrung);
		cbxNoiDen.getItems().addAll(dataCBXMienNam);
		cbxPhuongTien.getItems().addAll(dataPhuongTien);
		cbxNoiDen.getSelectionModel().select(1);
		cbxNoiDi.getSelectionModel().select(1);
		cbxPhuongTien.getSelectionModel().select(1);
		cbxSoLuong.getItems().addAll("10", "20", "25", "30", "50");
		cbxSoLuong.getSelectionModel().select(1);
		txtNgayKhoiHanh.setText(LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		;
		txtGioKhoiHanh.setText("06:00");
		try {
			Services services = new Services();
			HuongDanVienServices huongDanVienServices = services.getHuongDanVienServices();
			danhsachHuongDanVien = huongDanVienServices.danhSachHuongDanVien();

			TourServices tourServices = services.getTourServices();
			danhSachTour = tourServices.danhsachTour();
		} catch (Exception e) {
		}
		cbxHuongDanVien.getItems().setAll(danhsachHuongDanVien);
		cbxHuongDanVien.setCellFactory(new Callback<ListView<HuongDanVien>, ListCell<HuongDanVien>>() {

			@Override
			public ListCell<HuongDanVien> call(ListView<HuongDanVien> l) {
				return new ListCell<HuongDanVien>() {

					@Override
					protected void updateItem(HuongDanVien item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getHoTenHDV());
						}
					}
				};
			}
		});

		cbxHuongDanVien.setConverter(new StringConverter<HuongDanVien>() {
			private Map<String, HuongDanVien> map = new HashMap<>();

			@Override
			public String toString(HuongDanVien object) {
				if (object != null) {
					String name = object.getHoTenHDV();
					map.put(name, object);
					return name;
				} else
					return "";
			}

			@Override
			public HuongDanVien fromString(String string) {
				if (!map.containsKey(string)) {
					cbxHuongDanVien.setValue(null);
					cbxHuongDanVien.getEditor().clear();
					return null;
				}
				return map.get(string);
			}
		});
		txtGiaVe.textProperty().addListener((o, oldVal, newVal) -> {
			if (newVal != null) {
				newVal = newVal.replaceAll("[.]", "\b");
				txtGiaVe.setText(newVal);
			}
		});

		init();

	}

	public void setValues(Tour tour) {
		try {
			Services services = new Services();
			HuongDanVienServices huongDanVienServices = services.getHuongDanVienServices();
			cbxHuongDanVien.getItems().setAll(huongDanVienServices.danhSachHuongDanVien());
		} catch (Exception e) {
		}

		btnXacNhanMoTa.setSelected(true);
		btnXacNhanCST.setSelected(true);
		btnXacNhanCTT.setSelected(true);
		this.tour = tour;
		txtTenTour.setText(tour.getTenTour());
		cbxNoiDi.getItems().add(tour.getNoiDi());
		cbxNoiDi.setValue(tour.getNoiDi());
		cbxNoiDen.getItems().add(tour.getNoiDen());
		cbxNoiDen.setValue(tour.getNoiDen());
		cbxPhuongTien.getItems().add(tour.getPhuongTien());
		cbxPhuongTien.setValue(tour.getPhuongTien());
		txtNgayKhoiHanh.setText(tour.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtNgayKetThuc.setText(tour.getNgayKetThuc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtGioKhoiHanh.setText(tour.getGioKhoiHanh().format(DateTimeFormatter.ofPattern("hh:mm")));
		txtGiaVe.setText(tour.getGiaVe());
		if (tour.getAnh() != null) {
			byteAnhDaiDien = tour.getAnh();
			loadAnhDaiDien();
		}
		cbxSoLuong.setValue(Integer.toString(tour.getSoLuongHanhKhach()));
		try {
			Services services = new Services();
			ChiTietTourServices chiTietTourServices = services.getChiTietTourServices();
			this.chiTietTour = chiTietTourServices.timChiTietTour(tour.getMaTour());

		} catch (Exception e) {
			e.printStackTrace();
		}

		editorMoTa.setHtmlText(chiTietTour.getMoTa());
		editorChuongTrinhTour.setHtmlText(chiTietTour.getLichTrinh());
		editorChinhSachTour.setHtmlText(chiTietTour.getGhiChu());
		if (chiTietTour.getAnh().equals("") == false) {
			List<String> danhSachStringByte = getLinkAnh(chiTietTour.getAnh());
			danhSachByteAnh = convertArrStrtoArrBy(danhSachStringByte);
			loadDanhSachAnh();
		}
		try {
			int index = danhsachHuongDanVien.indexOf(tour.getHuongDanVien());
			cbxHuongDanVien.setValue(danhsachHuongDanVien.get(index));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void init() {
		txtTenTour.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				if (!txtTenTour.getText().equals("")) {
					lblErrorTenTour.setText("");
				} else {
					lblErrorTenTour.setText("Chưa nhập tên Tour");
				}
			}
		});

		cbxNoiDi.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				cbxNoiDi.validate();
			}
		});

		cbxNoiDen.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				cbxNoiDen.validate();
			}
		});

		cbxPhuongTien.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				cbxPhuongTien.validate();
			}
		});

		cbxSoLuong.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				cbxSoLuong.validate();
			}
		});

		txtNgayKhoiHanh.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				if (!txtNgayKhoiHanh.getText().equals("")) {
					String regex = "\\d{1,2}/\\d{1,2}/\\d{4}$";
					if (!txtNgayKhoiHanh.getText().matches(regex)) {
						lblErrorNgayKhoiHanh.setText("Ngày chưa hợp lệ");
					} else {
						try {
							String r1 = "\\d{1}/\\d{1}/\\d{4}$";
							String r2 = "\\d{2}/\\d{1}/\\d{4}$";
							String r3 = "\\d{1}/\\d{2}/\\d{4}$";
							String[] temp = txtNgayKhoiHanh.getText().split("/");
							String tempNgaySinh = "";
							if (txtNgayKhoiHanh.getText().matches(r1)) {
								tempNgaySinh += "0" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgayKhoiHanh.getText().matches(r2)) {
								tempNgaySinh += "" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgayKhoiHanh.getText().matches(r3)) {
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
							if (!localDate.format(formatter).equals(tempNgaySinh)) {
								lblErrorNgayKhoiHanh.setText("Ngày không tồn tại");
								txtNgayKetThuc.setText("");
							} else if (localDate.minus(1, ChronoUnit.DAYS).compareTo(LocalDate.now()) < 0) {
								lblErrorNgayKhoiHanh
										.setText("Ngày khởi hành phải lớn hơn ngày hiện tại 1 ngày");
								txtNgayKetThuc.setText("");
							} else {
								lblErrorNgayKhoiHanh.setText("");
								txtNgayKhoiHanh.setText(tempNgaySinh);
								txtNgayKetThuc.setText(
										LocalDate.parse(tempNgaySinh, formatter).plusDays(3).format(formatter));
								reloadHuongDanVien(localDate, LocalDate.parse(txtNgayKetThuc.getText(), formatter));
							}
						} catch (Exception e) {
							lblErrorNgayKhoiHanh.setText("Ngày không tồn tại");
							txtNgayKetThuc.setText("");
						}
					}
				} else {
					lblErrorNgayKhoiHanh.setText("Chưa nhập ngày khởi hành");
					txtNgayKetThuc.setText("");
				}
			}
		});

		txtNgayKetThuc.focusedProperty().addListener((o, oldV, newV) -> {
			if (!newV) {
				if (!txtNgayKetThuc.getText().equals("")) {
					String regex = "\\d{1,2}/\\d{1,2}/\\d{4}$";
					if (!txtNgayKetThuc.getText().matches(regex)) {
						lblErrorNgayKetThuc.setText("Ngày chưa hợp lệ");
					} else {
						try {
							String r1 = "\\d{1}/\\d{1}/\\d{4}$";
							String r2 = "\\d{2}/\\d{1}/\\d{4}$";
							String r3 = "\\d{1}/\\d{2}/\\d{4}$";
							String[] temp = txtNgayKetThuc.getText().split("/");
							String tempNgaySinh = "";
							if (txtNgayKetThuc.getText().matches(r1)) {
								tempNgaySinh += "0" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgayKetThuc.getText().matches(r2)) {
								tempNgaySinh += "" + temp[0];
								tempNgaySinh += "/0" + temp[1];
								tempNgaySinh += "/" + temp[2];
							} else if (txtNgayKetThuc.getText().matches(r3)) {
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
							if (!localDate.format(formatter).equals(tempNgaySinh)) {
								lblErrorNgayKetThuc.setText("Ngày không tồn tại");
							} else if (localDate.compareTo(LocalDate.parse(txtNgayKhoiHanh.getText(), formatter)) < 0) {
								lblErrorNgayKetThuc.setText("Ngày kết thúc phải lớn hơn ngày khởi hành");
							} else {
								lblErrorNgayKetThuc.setText("");
								reloadHuongDanVien(LocalDate.parse(txtNgayKhoiHanh.getText(), formatter), localDate);
							}
						} catch (Exception e) {
							lblErrorNgayKetThuc.setText("Ngày không tồn tại");
						}
					}
				} else {
					lblErrorNgayKetThuc.setText("Chưa nhập ngày kết thúc");
				}
			}
		});

		txtGioKhoiHanh.textProperty().addListener((o, oldV, newV) -> {
			if (!txtGioKhoiHanh.getText().equals("")) {
				String regex = "\\d{1,2}:\\d{2}$";
				if (!txtGioKhoiHanh.getText().matches(regex)) {
					lblErrorGioKhoiHanh.setText("Giờ chưa hợp lệ");
				} else {
					try {
						String r1 = "\\d{1}:\\d{2}";
						String[] temp = txtGioKhoiHanh.getText().split(":");
						String tempGioKhoiHanh = "";
						if (txtGioKhoiHanh.getText().matches(r1)) {
							tempGioKhoiHanh += "0" + temp[0];
							tempGioKhoiHanh += ":" + temp[1];
						} else {
							tempGioKhoiHanh += "" + temp[0];
							tempGioKhoiHanh += ":" + temp[1];
						}
						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
						LocalTime localTime = LocalTime.parse(tempGioKhoiHanh, timeFormatter);
						if (localTime != null) {
							lblErrorGioKhoiHanh.setText("");
						}
					} catch (Exception e) {
						lblErrorGioKhoiHanh.setText("Giờ không tồn tại");

					}
				}
			} else {
				lblErrorGioKhoiHanh.setText("Chưa nhập Giờ khởi hành");
			}
		});

		txtGiaVe.textProperty().addListener((o, oldV, newV) -> {
			if (!txtGiaVe.getText().equals("")) {
				try {
					Long.parseLong(txtGiaVe.getText());
					lblErrorGiaVe.setText("");
				} catch (Exception e) {
					lblErrorGiaVe.setText("Giá vé không hợp lệ");
				}
			} else {
				lblErrorGiaVe.setText("Chưa nhập Giá vé");
			}
		});

		cbxHuongDanVien.valueProperty().addListener((o, oldV, newV) -> {
			if (cbxHuongDanVien.getValue() == null) {
				cbxHuongDanVien.validate();
			}
		});

		btnThemTour.disableProperty().bind(txtTenTour.textProperty().isEmpty().or(cbxNoiDi.valueProperty().isNull())
				.or(cbxNoiDi.getEditor().textProperty().isEmpty()).or(cbxNoiDen.valueProperty().isNull())
				.or(cbxNoiDen.getEditor().textProperty().isEmpty()).or(cbxPhuongTien.valueProperty().isNull())
				.or(cbxPhuongTien.getEditor().textProperty().isEmpty()).or(cbxSoLuong.valueProperty().isNull())
				.or(cbxSoLuong.getEditor().textProperty().isEmpty()).or(txtNgayKhoiHanh.textProperty().isEmpty())
				.or(txtNgayKetThuc.textProperty().isEmpty()).or(txtGioKhoiHanh.textProperty().isEmpty())
				.or(txtGiaVe.textProperty().isEmpty()).or(cbxHuongDanVien.valueProperty().isNull())
				.or(lblErrorTenTour.textProperty().isNotEmpty()).or(lblErrorNoiKhoiHanh.textProperty().isNotEmpty())
				.or(lblErrorNoiDen.textProperty().isNotEmpty()).or(lblErrorPhuongTien.textProperty().isNotEmpty())
				.or(lblErrorSoLuong.textProperty().isNotEmpty()).or(lblErrorNgayKhoiHanh.textProperty().isNotEmpty())
				.or(lblErrorNgayKetThuc.textProperty().isNotEmpty()).or(lblErrorGioKhoiHanh.textProperty().isNotEmpty())
				.or(lblErrorGiaVe.textProperty().isNotEmpty()).or(lblErrorHuongDanVien.textProperty().isNotEmpty()));

		editorMoTa.disableProperty().bind(btnXacNhanMoTa.selectedProperty());
		editorChinhSachTour.disableProperty().bind(btnXacNhanCST.selectedProperty());
		editorChuongTrinhTour.disableProperty().bind(btnXacNhanCTT.selectedProperty());

	}

	private List<byte[]> convertArrStrtoArrBy(List<String> danhSachStringByte) {
		List<byte[]> list = new ArrayList<>();
		for (int i = 0, len = danhSachStringByte.size(); i < len; i++) {
			byte[] temp = convertStringtoByte(danhSachStringByte.get(i).split(","));
			list.add(temp);
		}
		return list;
	}

	private byte[] convertStringtoByte(String[] strings) {
		try {
			byte[] bs = new byte[strings.length];
			for (int i = 0, len = bs.length; i < len; i++) {
				bs[i] = Byte.parseByte(strings[i].trim());
			}
			return bs;
		} catch (Exception e) {
		}
		return null;
	}

	private List<String> getLinkAnh(String linkAnh) {
		String[] temp = linkAnh.split("\\], \\[");
		List<String> danhSachTemp = new ArrayList<>();
		for (int i = 0, len = temp.length; i < len; i++) {
			danhSachTemp.add(temp[i].replaceAll("\\[", "").replaceAll("\\]", ""));
		}
		return danhSachTemp;
	}

	private void reloadHuongDanVien(LocalDate ngayDi, LocalDate ngayVe) {
		tempDanhSachHuongDanVien = new ArrayList<>();
		tempDanhSachHuongDanVien = (ArrayList<HuongDanVien>) danhsachHuongDanVien;
		danhSachTour.forEach(x -> {
			boolean t1 = ngayDi.compareTo(x.getNgayKhoiHanh()) < 0 && ngayVe.compareTo(x.getNgayKhoiHanh()) > 0
					&& ngayVe.compareTo(x.getNgayKetThuc()) <= 0;
			boolean t2 = ngayDi.compareTo(x.getNgayKhoiHanh()) >= 0 && ngayDi.compareTo(x.getNgayKetThuc()) < 0
					&& ngayVe.compareTo(x.getNgayKetThuc()) > 0;
			boolean t3 = ngayDi.compareTo(x.getNgayKhoiHanh()) >= 0 && ngayVe.compareTo(x.getNgayKetThuc()) <= 0;
			if (t1 == true || t2 == true || t3 == true) {
				tempDanhSachHuongDanVien.remove(x.getHuongDanVien());
				cbxHuongDanVien.getItems().clear();
				cbxHuongDanVien.getItems().addAll(tempDanhSachHuongDanVien);
			} else {
				cbxHuongDanVien.getItems().clear();
				cbxHuongDanVien.getItems().addAll(danhsachHuongDanVien);
			}
		});
	}

	@FXML
	private void handlButtonAction(MouseEvent e) {
		if (e.getSource() == btnClose) {

			/**
			 * Alert cảnh báo đóng Giao diện
			 */
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Do you want to exit?");
			alert.setContentText("Are you sure?");
			javafx.scene.control.ButtonType yesBtn = new javafx.scene.control.ButtonType("Yes", ButtonData.YES);
			javafx.scene.control.ButtonType noBtn = new javafx.scene.control.ButtonType("Yes", ButtonData.NO);
			alert.getButtonTypes().setAll(yesBtn, noBtn);

			if (alert.showAndWait().get() == yesBtn) {
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			} else
				alert.close();
		} else if (e.getSource() == btnThemTour) {
			if (themTour() == true) {
				this.flag = true;
				alertError(AlertType.INFORMATION, "Thêm Tour thành công", "Thao tác thành công", "");
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			} else {
				alertError(AlertType.ERROR, "Thêm Tour không thành công", "Thao tác không thành công",
						"Không thể thêm Tour thành công");
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}

		} else if (e.getSource() == btnCapNhat) {
			if (capNhatTour() == true) {
				this.flag = true;
				alertError(AlertType.CONFIRMATION, "Sửa Tour thành công", "Thao tác thành công", "");
				Node node = (Node) e.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
		} else if (e.getSource() == btnThemAnh) {
			try {
//				filepath = new File("D:\\Lib Eclipse\\Neon\\PTUD_Project");
//				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
//				fileChooser.setInitialDirectory(filepath);
				List<File> danhSachFile = fileChooser.showOpenMultipleDialog(null);
				danhSachFile.forEach(x -> {
					if (!danhSachFileAnh.contains(x)) {
						try {
							byte[] bs = Files.readAllBytes(x.toPath());
							danhSachByteAnh.add(bs);
							danhSachFileAnh.add(x);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				});
				loadDanhSachAnh();
			} catch (Exception e2) {
			}

		} else if (e.getSource() == btnThemAnhDaiDien) {
			try {
				fileAnhDaiDien = fileChooser.showOpenDialog(null);
				byteAnhDaiDien = Files.readAllBytes(fileAnhDaiDien.toPath());
				loadAnhDaiDien();
			} catch (Exception e2) {
			}
		} else if (e.getSource() == btnReload) {
			loadDanhSachAnh();
		} else if (e.getSource() == btnDemo) {
			demo();
		}
	}

	private void loadAnhDaiDien() {
		try {
			ArrayList<Node> children = new ArrayList<>();
			InputStream inputStream = new ByteArrayInputStream(byteAnhDaiDien);
			Image image = new Image(inputStream, 900, 0, true, true);
			StackPane child = new StackPane();
			JFXDepthManager.setDepth(child, 1);
			children.add(child);
			StackPane header = new StackPane();

			ImageView imageView = new ImageView(image);
			header.getChildren().add(imageView);
			VBox.setVgrow(header, Priority.ALWAYS);
			StackPane body = new StackPane();
			body.setMinHeight(50);
			VBox content = new VBox();
			content.getChildren().addAll(header, body);
			body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");

			// create button
			JFXButton button = new JFXButton("");
			button.setButtonType(ButtonType.RAISED);
			button.setStyle("-fx-background-radius: 40;-fx-background-color: "
					+ getDefaultColor((int) ((Math.random() * 12) % 12)));
			button.setPrefSize(40, 40);
			button.setScaleX(0);
			button.setScaleY(0);
			SVGGlyph glyph = new SVGGlyph(-1, "test",
					"M336.559,68.611L231.016,174.165l105.543,105.549c15.699,15.705,15.699,41.145,0,56.85\r\n"
							+ "		c-7.844,7.844-18.128,11.769-28.407,11.769c-10.296,0-20.581-3.919-28.419-11.769L174.167,231.003L68.609,336.563\r\n"
							+ "		c-7.843,7.844-18.128,11.769-28.416,11.769c-10.285,0-20.563-3.919-28.413-11.769c-15.699-15.698-15.699-41.139,0-56.85\r\n"
							+ "		l105.54-105.549L11.774,68.611c-15.699-15.699-15.699-41.145,0-56.844c15.696-15.687,41.127-15.687,56.829,0l105.563,105.554\r\n"
							+ "		L279.721,11.767c15.705-15.687,41.139-15.687,56.832,0C352.258,27.466,352.258,52.912,336.559,68.611z",
					Color.WHITE);
			glyph.setSize(20, 20);
			button.setGraphic(glyph);
			button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
				return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
			}, header.boundsInParentProperty(), button.heightProperty()));
			StackPane.setAlignment(button, Pos.TOP_RIGHT);
			Timeline animation = new Timeline(
					new KeyFrame(Duration.millis(240), new KeyValue(button.scaleXProperty(), 1, EASE_BOTH),
							new KeyValue(button.scaleYProperty(), 1, EASE_BOTH)));
			animation.setDelay(Duration.millis(100 * 1 + 1000));
			animation.play();
			child.getChildren().addAll(content, button);
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					children.remove(0);

					loadAnhDaiDien();

					Platform.runLater(() -> {
						tab.requestLayout();
					});
				}
			});
			masonryAnhDaiDien.getChildren().clear();
			masonryAnhDaiDien.getChildren().addAll(children);
			Platform.runLater(() -> {
				tab.requestLayout();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadDanhSachAnh() {
		try {

			ArrayList<Node> children = new ArrayList<>();
			for (int i = 0; i < danhSachByteAnh.size(); i++) {
				/**
				 * File Anh
				 */
				Image image = null;
				InputStream inputStream = new ByteArrayInputStream(danhSachByteAnh.get(i));
				image = new Image(inputStream, 300, 0, true, true);

				/**
				 * Tao cac node chua anh
				 */
				StackPane child = new StackPane();
				JFXDepthManager.setDepth(child, 1);
				children.add(child);

				/**
				 * Node chua anh
				 */
				// create content
				StackPane header = new StackPane();

				ImageView imageView = new ImageView(image);
				header.getChildren().add(imageView);
				VBox.setVgrow(header, Priority.ALWAYS);
				StackPane body = new StackPane();
				body.setMinHeight(50);
				VBox content = new VBox();
				content.getChildren().addAll(header, body);
				body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");

				// create button
				JFXButton button = new JFXButton("");
				button.setButtonType(ButtonType.RAISED);
				button.setStyle("-fx-background-radius: 40;-fx-background-color: "
						+ getDefaultColor((int) ((Math.random() * 12) % 12)));
				button.setPrefSize(40, 40);
				button.setScaleX(0);
				button.setScaleY(0);
				SVGGlyph glyph = new SVGGlyph(-1, "test",
						"M336.559,68.611L231.016,174.165l105.543,105.549c15.699,15.705,15.699,41.145,0,56.85\r\n"
								+ "		c-7.844,7.844-18.128,11.769-28.407,11.769c-10.296,0-20.581-3.919-28.419-11.769L174.167,231.003L68.609,336.563\r\n"
								+ "		c-7.843,7.844-18.128,11.769-28.416,11.769c-10.285,0-20.563-3.919-28.413-11.769c-15.699-15.698-15.699-41.139,0-56.85\r\n"
								+ "		l105.54-105.549L11.774,68.611c-15.699-15.699-15.699-41.145,0-56.844c15.696-15.687,41.127-15.687,56.829,0l105.563,105.554\r\n"
								+ "		L279.721,11.767c15.705-15.687,41.139-15.687,56.832,0C352.258,27.466,352.258,52.912,336.559,68.611z",
						Color.WHITE);
				glyph.setSize(20, 20);
				button.setGraphic(glyph);
				button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
					return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
				}, header.boundsInParentProperty(), button.heightProperty()));
				// StackPane.setMargin(button, new Insets(0, 12, 0, 0));
				StackPane.setAlignment(button, Pos.TOP_RIGHT);
				Timeline animation = new Timeline(
						new KeyFrame(Duration.millis(240), new KeyValue(button.scaleXProperty(), 1, EASE_BOTH),
								new KeyValue(button.scaleYProperty(), 1, EASE_BOTH)));

				animation.setDelay(Duration.millis(100 * i + 1000));
				animation.play();
				child.getChildren().addAll(content, button);
				/**
				 * Tạo Action cho tất cả button
				 */
				int temp = i;
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						danhSachByteAnh.remove(temp);
						loadDanhSachAnh();
						Platform.runLater(() -> {
							tab.requestLayout();
						});
					}
				});

			}
			masonryDanhSachAnh.getChildren().clear();
			masonryDanhSachAnh.getChildren().setAll(children);
			Platform.runLater(() -> {
				tab.requestLayout();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void alertError(AlertType type, String txtTitle, String txtHead, String txtContext) {
		Alert alert = new Alert(type);
		alert.setTitle(txtTitle);
		alert.setHeaderText(txtHead);
		alert.setContentText(txtContext);
		alert.showAndWait();
	}

	private List<String> convertFiletoString() {
		List<String> strings = new ArrayList<>();
		danhSachByteAnh.forEach(x -> {
			try {
				strings.add(Arrays.toString(x));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return strings;
	}

	private void demo() {
		String tenTour = (txtTenTour.getText() == null) ? "" : txtTenTour.getText();
		String noiKhoiHanh = (cbxNoiDi.getValue() == null) ? "" : cbxNoiDi.getValue();
		String noiDen = (cbxNoiDen.getValue() == null) ? "" : cbxNoiDen.getValue();
		String phuongTien = (cbxPhuongTien.getValue() == null) ? "" : cbxPhuongTien.getValue();
		int soLuong = (cbxSoLuong.getValue() == null) ? 0 : Integer.parseInt(cbxSoLuong.getValue());
		LocalDate ngayKhoiHanh = (txtNgayKhoiHanh.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKhoiHanh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate ngayKetThuc = (txtNgayKetThuc.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKetThuc.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime gioKhoiHanh = (txtGioKhoiHanh.getText().equals("")) ? LocalTime.now()
				: LocalTime.parse(txtGioKhoiHanh.getText(), DateTimeFormatter.ofPattern("HH:mm"));
		String giaVe = (txtGiaVe.getText() == null) ? "" : txtGiaVe.getText();
		HuongDanVien huongDanVien = (cbxHuongDanVien.getValue() == null) ? null : cbxHuongDanVien.getValue();

		Tour tour = new Tour("", tenTour, noiKhoiHanh, noiDen, ngayKhoiHanh, ngayKetThuc, gioKhoiHanh, phuongTien,
				giaVe, false, (byteAnhDaiDien == null) ? null : byteAnhDaiDien, soLuong, huongDanVien);

		String moTa = editorMoTa.getHtmlText();
		String lichTrinh = editorChuongTrinhTour.getHtmlText();
		String ghiChu = editorChinhSachTour.getHtmlText();
		ChiTietTour chiTietTour = new ChiTietTour("", moTa, lichTrinh, ghiChu, convertFiletoString().toString());

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ThongTinTour.fxml"));
			DialogPane dialogPane = new DialogPane();
			dialogPane = fxmlLoader.load();
			ThongTinTourControl thongTinTourControl = fxmlLoader.getController();
			thongTinTourControl.setValuesDemo(tour, chiTietTour);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Thông tin Tour");
			alert.setResizable(false);
			alert.setDialogPane(dialogPane);
			alert.initModality(Modality.APPLICATION_MODAL);
			Window window = alert.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());

			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean capNhatTour() {
		String tenTour = (txtTenTour.getText() == null) ? "" : txtTenTour.getText();
		String noiKhoiHanh = (cbxNoiDi.getValue() == null) ? "" : cbxNoiDi.getValue();
		String noiDen = (cbxNoiDen.getValue() == null)
				? ((cbxNoiDen.getEditor().getText().equals("") == true) ? "" : cbxNoiDen.getEditor().getText())
				: cbxNoiDen.getValue();
		String phuongTien = (cbxPhuongTien.getValue() == null)
				? ((cbxPhuongTien.getEditor().getText().equals("") == true) ? "" : cbxPhuongTien.getEditor().getText())
				: cbxPhuongTien.getValue();
		int soLuong = (cbxSoLuong.getValue() == null) ? ((cbxSoLuong.getEditor().getText().equals("") == true) ? 0
				: Integer.parseInt(cbxSoLuong.getEditor().getText())) : Integer.parseInt(cbxSoLuong.getValue());
		LocalDate ngayKhoiHanh = (txtNgayKhoiHanh.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKhoiHanh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate ngayKetThuc = (txtNgayKetThuc.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKetThuc.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime gioKhoiHanh = (txtGioKhoiHanh.getText().equals("")) ? LocalTime.now()
				: LocalTime.parse(txtGioKhoiHanh.getText(), DateTimeFormatter.ofPattern("HH:mm"));
		String giaVe = (txtGiaVe.getText() == null) ? "" : txtGiaVe.getText();
		HuongDanVien huongDanVien = (cbxHuongDanVien.getValue() == null) ? null : cbxHuongDanVien.getValue();

		Tour newTour = new Tour(tour.getMaTour(), tenTour, noiKhoiHanh, noiDen, ngayKhoiHanh, ngayKetThuc, gioKhoiHanh,
				phuongTien, giaVe, false, (byteAnhDaiDien == null) ? null : byteAnhDaiDien, soLuong, huongDanVien);

		String moTa = editorMoTa.getHtmlText();
		String lichTrinh = editorChuongTrinhTour.getHtmlText();
		String ghiChu = editorChinhSachTour.getHtmlText();
		ChiTietTour newChiTietTour = new ChiTietTour(chiTietTour.getMaTour(), moTa, lichTrinh, ghiChu,
				convertFiletoString().toString());

		try {
			Services services = new Services();
			TourServices tourServices = services.getTourServices();

			return tourServices.suaTour(newTour, newChiTietTour);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean themTour() {
		String tenTour = (txtTenTour.getText() == null) ? "" : txtTenTour.getText();
		String noiKhoiHanh = (cbxNoiDi.getValue() == null) ? "" : cbxNoiDi.getValue();
		String noiDen = (cbxNoiDen.getValue() == null)
				? ((cbxNoiDen.getEditor().getText().equals("") == true) ? "" : cbxNoiDen.getEditor().getText())
				: cbxNoiDen.getValue();
		String phuongTien = (cbxPhuongTien.getValue() == null)
				? ((cbxPhuongTien.getEditor().getText().equals("") == true) ? "" : cbxPhuongTien.getEditor().getText())
				: cbxPhuongTien.getValue();
		int soLuong = (cbxSoLuong.getValue() == null) ? ((cbxSoLuong.getEditor().getText().equals("") == true) ? 0
				: Integer.parseInt(cbxSoLuong.getEditor().getText())) : Integer.parseInt(cbxSoLuong.getValue());
		LocalDate ngayKhoiHanh = (txtNgayKhoiHanh.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKhoiHanh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate ngayKetThuc = (txtNgayKetThuc.getText().equals("")) ? LocalDate.now()
				: LocalDate.parse(txtNgayKetThuc.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalTime gioKhoiHanh = (txtGioKhoiHanh.getText().equals("")) ? LocalTime.now()
				: LocalTime.parse(txtGioKhoiHanh.getText(), DateTimeFormatter.ofPattern("HH:mm"));
		String giaVe = (txtGiaVe.getText() == null) ? "" : txtGiaVe.getText();
		HuongDanVien huongDanVien = (cbxHuongDanVien.getValue() == null) ? null : cbxHuongDanVien.getValue();

		Tour newTour = new Tour("", tenTour, noiKhoiHanh, noiDen, ngayKhoiHanh, ngayKetThuc, gioKhoiHanh, phuongTien,
				giaVe, false, (byteAnhDaiDien == null) ? null : byteAnhDaiDien, soLuong, huongDanVien);

		String moTa = editorMoTa.getHtmlText();
		String lichTrinh = editorChuongTrinhTour.getHtmlText();
		String ghiChu = editorChinhSachTour.getHtmlText();
		ChiTietTour newChiTietTour = new ChiTietTour("", moTa, lichTrinh, ghiChu, convertFiletoString().toString());

		try {
			Services services = new Services();
			TourServices tourServices = services.getTourServices();
			return tourServices.themTour(newTour, newChiTietTour);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean getResult() {
		return flag;
	}

	private String getDefaultColor(int i) {
		String color = "#FFFFFF";
		switch (i) {
		case 0:
			color = "#8F3F7E";
			break;
		case 1:
			color = "#B5305F";
			break;
		case 2:
			color = "#CE584A";
			break;
		case 3:
			color = "#DB8D5C";
			break;
		case 4:
			color = "#DA854E";
			break;
		case 5:
			color = "#E9AB44";
			break;
		case 6:
			color = "#FEE435";
			break;
		case 7:
			color = "#99C286";
			break;
		case 8:
			color = "#01A05E";
			break;
		case 9:
			color = "#4A8895";
			break;
		case 10:
			color = "#16669B";
			break;
		case 11:
			color = "#2F65A5";
			break;
		case 12:
			color = "#4E6A9C";
			break;
		default:
			break;
		}
		return color;
	}

}
