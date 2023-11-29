package com.Intumit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Intumit.model.Notice;
import com.Intumit.repo.INoticeRepo;

@Service
public class NoticeService {
	@Autowired
	private INoticeRepo repo;

	public List<Notice> getAllNotice() {
		ArrayList<Notice> noticeList = new ArrayList<>();
		repo.findAll().forEach(notice -> noticeList.add(notice));
		
		return noticeList;
	}
	
	public Notice getNoticeById(Long id) {
		return repo.findById(id).get();
	}
	
	public boolean updateStatus(Long id) {
		Notice notice = getNoticeById(id);
		
		return saveOrUpdateNotice(notice);
	}
	
	public boolean saveOrUpdateNotice(Notice notice) {
		Notice updatedObj = repo.save(notice);
		
		if (getNoticeById(updatedObj.getNotice_id()) != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean deleteNotice(Long id) {
		repo.deleteById(id);
		
		if (repo.findById(id).isEmpty()) {
			return true;
		}
		
		return false;
	}
}
