package org.shotpatterns.data;

import java.util.List;

import org.shotpatterns.FileDatabaseHandler;
import org.shotpatterns.exception.InsufficientDataException;

public class MovieData {

	private String title;
	private ShotData ots;
	private ShotData ecu;
	private ShotData cu;
	private ShotData mcu;
	private ShotData ms;
	private ShotData mls;
	private ShotData ls;
	private ShotData els;
	private ShotData ins;

	public String getTitle() {
		return title;
	}

	public MovieData(String title, ShotData... datas) throws InsufficientDataException {
		if (datas.length != 9) {
			throw new InsufficientDataException();
		}
		this.title = title;
		this.ots = datas[0];
		this.ecu = datas[1];
		this.cu = datas[2];
		this.mcu = datas[3];
		this.ms = datas[4];
		this.mls = datas[5];
		this.ls = datas[6];
		this.els = datas[7];
		this.ins = datas[8];
	}

	public MovieData(String title, List<ShotData> shotData) throws InsufficientDataException {
		if (shotData.size() != 9) {
			throw new InsufficientDataException();
		}
		this.title = title;
		ots = shotData.get(0);
		ecu = shotData.get(1);
		cu = shotData.get(2);
		mcu = shotData.get(3);
		ms = shotData.get(4);
		mls = shotData.get(5);
		ls = shotData.get(6);
		els = shotData.get(7);
		ins = shotData.get(8);
	}

	public ShotData getOts() {
		return ots;
	}

	public ShotData getEcu() {
		return ecu;
	}

	public ShotData getCu() {
		return cu;
	}

	public ShotData getMcu() {
		return mcu;
	}

	public ShotData getMs() {
		return ms;
	}

	public ShotData getMls() {
		return mls;
	}

	public ShotData getLs() {
		return ls;
	}

	public ShotData getEls() {
		return els;
	}

	public ShotData getIns() {
		return ins;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		MovieData data = (MovieData) obj;

		return ots.equals(data.ots) && ecu.equals(data.ecu) && cu.equals(data.cu) && mcu.equals(data.mcu)
		        && ms.equals(data.ms) && mls.equals(data.mls) && ls.equals(data.ls) && els.equals(data.els)
		        && ins.equals(data.ins);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(title);
		buffer.append(FileDatabaseHandler.SEPARATOR + ots.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + ecu.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + cu.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + mcu.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + ms.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + mls.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + ls.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + els.getPercentage());
		buffer.append(FileDatabaseHandler.SEPARATOR + ins.getPercentage());

		return buffer.toString();
	}
}
