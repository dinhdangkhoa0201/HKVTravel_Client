package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.effects.JFXDepthManager;

import application.Services;
import entities.ChiTietTour;
import entities.Tour;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import services.ChiTietTourServices;
import services.imp.ChiTietTourImp;

public class ThongTinTourControl implements Initializable {
	@FXML
	private Label lblMaTour;
	@FXML
	private Label lblTenTour;
	@FXML
	private Label lblNoiDi;
	@FXML
	private Label lblNoiDen;
	@FXML
	private Label lblNgayKhoiHanh;
	// @FXML private Label lblNgayKetThuc;
	@FXML
	private Label lblGioKhoiHanh;
	@FXML
	private Label lblPhuongTien;
	@FXML
	private Label lblGiaVe;
	@FXML
	private ScrollPane scroll;
	@FXML
	private JFXMasonryPane masonryAnhDaiDien;
	@FXML
	private JFXMasonryPane masonryHinhAnh;
	@FXML
	private WebView webViewMoTa;
	@FXML
	private WebView webViewChuongTrinhTour;
	@FXML
	private WebView webViewChinhSachTour;
	@FXML
	private AnchorPane main;
	
	private WebEngine engineMoTa;
	private WebEngine engineChuongTrinhTour;
	private WebEngine engineChinhSachTour;

	private ChiTietTour chiTietTour;

	private byte[] byteAnh;
	private List<byte[]> danhSachByteAnh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		engineMoTa = webViewMoTa.getEngine();
		engineChuongTrinhTour = webViewChuongTrinhTour.getEngine();
		engineChinhSachTour = webViewChinhSachTour.getEngine();

		webViewMoTa.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> change) {
				Set<Node> deadSeaScrolls = webViewMoTa.lookupAll(".scroll-bar");
				for (Node scroll : deadSeaScrolls) {
					scroll.setVisible(false);
				}
			}
		});

		webViewChuongTrinhTour.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> change) {
				Set<Node> deadSeaScrolls = webViewChuongTrinhTour.lookupAll(".scroll-bar");
				for (Node scroll : deadSeaScrolls) {
					scroll.setVisible(false);
				}
			}
		});

		webViewChinhSachTour.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> change) {
				Set<Node> deadSeaScrolls = webViewChinhSachTour.lookupAll(".scroll-bar");
				for (Node scroll : deadSeaScrolls) {
					scroll.setVisible(false);
				}
			}
		});

	}

	public void setValues(Tour tour) {
		try {
			lblMaTour.setText(tour.getMaTour());
			lblTenTour.setText(tour.getTenTour());
			lblNoiDi.setText(tour.getNoiDi());
			lblNoiDen.setText(tour.getNoiDen());
			lblNgayKhoiHanh.setText(tour.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			lblGioKhoiHanh.setText(tour.getGioKhoiHanh().toString());
			lblGiaVe.setText(tour.getGiaVe());
			byteAnh = tour.getAnh();
			loadAnhDaiDien();

			Services services = new Services();
			ChiTietTourServices chiTietTourImp = services.getChiTietTourServices();
			chiTietTour = chiTietTourImp.timChiTietTour(tour.getMaTour());
			engineMoTa.loadContent(chiTietTour.getMoTa());
			engineChuongTrinhTour.loadContent(chiTietTour.getLichTrinh());
			engineChinhSachTour.loadContent(chiTietTour.getGhiChu());
			;
			if (chiTietTour.getAnh().equals("") == false) {
				danhSachByteAnh = convertArrStrtoArrBy(getLinkAnh(chiTietTour.getAnh()));
				loadDanhSachAnh();
			}
			Platform.runLater(() -> {
				scroll.setVvalue(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setValuesDemo(Tour tour, ChiTietTour chiTietTour) {
		try {
			System.out.println("Hello");
			lblMaTour.setText(tour.getMaTour());
			lblTenTour.setText(tour.getTenTour());
			lblNoiDi.setText(tour.getNoiDi());
			lblNoiDen.setText(tour.getNoiDen());
			lblNgayKhoiHanh.setText(tour.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			lblGioKhoiHanh.setText(tour.getGioKhoiHanh().toString());
			lblGiaVe.setText(tour.getGiaVe());
			if(tour.getAnh() != null) {
				byteAnh = tour.getAnh();
				loadAnhDaiDien();
			}

			engineMoTa.loadContent(chiTietTour.getMoTa());
			engineChuongTrinhTour.loadContent(chiTietTour.getLichTrinh());
			engineChinhSachTour.loadContent(chiTietTour.getGhiChu());
			if (chiTietTour.getAnh().equals("") == false) {
				List<String> danhSachStringByte = getLinkAnh(chiTietTour.getAnh());
				danhSachByteAnh = convertArrStrtoArrBy(danhSachStringByte);
				loadDanhSachAnh();
			}
			Platform.runLater(() -> {
				scroll.setVvalue(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loaDataFromDatabase(ChiTietTour chiTietTour) {
		engineMoTa.loadContent(chiTietTour.getMoTa());
		engineChuongTrinhTour.loadContent(chiTietTour.getLichTrinh());
		engineChinhSachTour.loadContent(chiTietTour.getGhiChu());
		;
	}

	private List<byte[]> convertArrStrtoArrBy(List<String> danhSachStringByte) {
		List<byte[]> list = new ArrayList<>();
		for (int i = 0, len = danhSachStringByte.size(); i < len; i++) {
			byte[] temp = convertStringtoByte(danhSachStringByte.get(i).split(","));
			list.add(temp);
		}
		System.out.println(list.size());
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



	private void loadAnhDaiDien() {
		masonryAnhDaiDien.getChildren().clear();
		ArrayList<Node> children = new ArrayList<>();
		try {
			InputStream inputStream = new ByteArrayInputStream(byteAnh);
			Image image = new Image(inputStream, 1366, 0, true, true);

			StackPane child = new StackPane();
			JFXDepthManager.setDepth(child, 1);
			children.add(child);

			StackPane header = new StackPane();

			ImageView imageView = new ImageView(image);
			header.getChildren().add(imageView);
			VBox.setVgrow(header, Priority.ALWAYS);
			VBox content = new VBox();
			content.getChildren().addAll(header);
			child.getChildren().addAll(content);
			masonryAnhDaiDien.getChildren().addAll(children);
			Platform.runLater(() -> {
				scroll.setVvalue(0);
			});
		} catch (Exception e) {
			
		}

	}

	private void loadDanhSachAnh() {
		try {
			// masonryDanhSachAnh = new JFXMasonryPane();
			ArrayList<Node> children = new ArrayList<>();
			for (int i = 0; i < danhSachByteAnh.size(); i++) {
				/**
				 * File Anh
				 */

				InputStream inputStream = new ByteArrayInputStream(danhSachByteAnh.get(i));
				Image image = new Image(inputStream, 300, 0, true, true);

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

				ImageView imageView = new ImageView(image);
				StackPane header = new StackPane();
				header.getChildren().add(imageView);

				VBox.setVgrow(header, Priority.ALWAYS);
				VBox content = new VBox();
				content.getChildren().addAll(header);
				child.getChildren().addAll(content);

			}
			masonryHinhAnh.getChildren().addAll(children);
			Platform.runLater(() -> {
				scroll.setVvalue(0);
			});
		} catch (Exception e) {
			
		}
	}

	@FXML
	public void handleButtonAction() {

	}

	@SuppressWarnings("unused")
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
