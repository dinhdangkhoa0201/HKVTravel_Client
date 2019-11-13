package application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import services.ChiTietTourServices;
import services.HoaDonServices;
import services.HuongDanVienServices;
import services.KhachHangServices;
import services.KhachHangThamGiaServices;
import services.NhanVienServices;
import services.TourServices;
import services.UserPasswordServices;

public class Services {
	private ChiTietTourServices chiTietTourServices;
	private HoaDonServices hoaDonServices;
	private HuongDanVienServices huongDanVienServices;
	private KhachHangServices khachHangServices;
	private KhachHangThamGiaServices khachHangThamGiaServices;
	private NhanVienServices nhanVienServices;
	private TourServices tourServices;
	private UserPasswordServices userPasswordServices;
	private static final String HOST = "localhost";
	private static final String PORT = "1999";

	public void layService() {
		try {
			System.setProperty("java.security.policy", "policy\\mypolicy.policy");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			SecurityManager securityManager = System.getSecurityManager();
			if (securityManager == null) {
				System.setProperty("java.security.policy", "policy\\mypolicy.policy");
				securityManager = new SecurityManager();
			}
		} catch (Exception e) {
			alert(AlertType.ERROR, "Lỗi", "Lỗi kết nối với server", "host");
		}
	}

	public Services() {
		try {
			
			System.setProperty("java.security.policy", "policy\\mypolicy.policy");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			SecurityManager securityManager = System.getSecurityManager();
			if (securityManager == null) {
				System.setProperty("java.security.policy", "policy\\mypolicy.policy");
				securityManager = new SecurityManager();
			}
			chiTietTourServices = (ChiTietTourServices) Naming.lookup("rmi://"+HOST+":"+PORT+"/chiTietTourServices");
			
			hoaDonServices = (HoaDonServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/hoaDonServices");
			
			huongDanVienServices = (HuongDanVienServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/huongDanVienServices");
			
			khachHangServices = (KhachHangServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/khachHangServices");
			
			khachHangThamGiaServices = (KhachHangThamGiaServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/khachHangThamGiaServices");
			
			nhanVienServices = (NhanVienServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/nhanVienServices");
			
			tourServices = (TourServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/tourServices");
			
			userPasswordServices = (UserPasswordServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/userPasswordServices");
		} catch (Exception e) {
			alert(AlertType.ERROR, "Lỗi", "Lỗi kết nối với server", "Không thể kết nối tới server");
			System.exit(0);
		}
	}

	/**
	 * @return the chiTietTourServices
	 */
	public ChiTietTourServices getChiTietTourServices() {
		try {
			chiTietTourServices = (ChiTietTourServices) Naming.lookup("rmi://"+HOST+":"+PORT+"/chiTietTourServices");
			return chiTietTourServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param chiTietTourServices the chiTietTourServices to set
	 */
	public void setChiTietTourServices(ChiTietTourServices chiTietTourServices) {
		this.chiTietTourServices = chiTietTourServices;
	}

	/**
	 * @return the hoaDonServices
	 */
	public HoaDonServices getHoaDonServices() {
		try {
			hoaDonServices = (HoaDonServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/hoaDonServices");
			return hoaDonServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param hoaDonServices the hoaDonServices to set
	 */
	public void setHoaDonServices(HoaDonServices hoaDonServices) {
		this.hoaDonServices = hoaDonServices;
	}

	/**
	 * @return the huongDanVienServices
	 */
	public HuongDanVienServices getHuongDanVienServices() {
		try {
			huongDanVienServices = (HuongDanVienServices) Naming
					.lookup("rmi://" + HOST + ":" + PORT + "/huongDanVienServices");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return huongDanVienServices;
	}

	/**
	 * @param huongDanVienServices the huongDanVienServices to set
	 */
	public void setHuongDanVienServices(HuongDanVienServices huongDanVienServices) {
		this.huongDanVienServices = huongDanVienServices;
	}

	/**
	 * @return the khachHangServices
	 */
	public KhachHangServices getKhachHangServices() {
		try {
			khachHangServices = (KhachHangServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/khachHangServices");
			return khachHangServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param khachHangServices the khachHangServices to set
	 */
	public void setKhachHangServices(KhachHangServices khachHangServices) {
		this.khachHangServices = khachHangServices;
	}

	/**
	 * @return the khachHangThamGiaServices
	 */
	public KhachHangThamGiaServices getKhachHangThamGiaServices() {
		try {
			khachHangThamGiaServices = (KhachHangThamGiaServices) Naming
					.lookup("rmi://" + HOST + ":" + PORT + "/khachHangThamGiaServices");
			return khachHangThamGiaServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param khachHangThamGiaServices the khachHangThamGiaServices to set
	 */
	public void setKhachHangThamGiaServices(KhachHangThamGiaServices khachHangThamGiaServices) {
		this.khachHangThamGiaServices = khachHangThamGiaServices;
	}

	/**
	 * @return the nhanVienServices
	 */
	public NhanVienServices getNhanVienServices() {
		try {
			nhanVienServices = (NhanVienServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/nhanVienServices");
			return nhanVienServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param nhanVienServices the nhanVienServices to set
	 */
	public void setNhanVienServices(NhanVienServices nhanVienServices) {
		this.nhanVienServices = nhanVienServices;
	}

	/**
	 * @return the tourServices
	 */
	public TourServices getTourServices() {
		try {
			tourServices = (TourServices) Naming.lookup("rmi://" + HOST + ":" + PORT + "/nhanVienServices");
			return tourServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param tourServices the tourServices to set
	 */
	public void setTourServices(TourServices tourServices) {
		this.tourServices = tourServices;
	}

	/**
	 * @return the userPasswordServices
	 */
	public UserPasswordServices getUserPasswordServices() {
		try {
			userPasswordServices = (UserPasswordServices) Naming
					.lookup("rmi://" + HOST + ":" + PORT + "/userPasswordServices");
			return userPasswordServices;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param userPasswordServices the userPasswordServices to set
	 */
	public void setUserPasswordServices(UserPasswordServices userPasswordServices) {
		this.userPasswordServices = userPasswordServices;
	}

	private static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
