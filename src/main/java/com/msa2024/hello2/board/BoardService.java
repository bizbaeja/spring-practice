package com.msa2024.hello2.board;

import com.msa2024.hello2.board.BoardFileMapper;
import com.msa2024.hello2.board.BoardImageFileMapper;
import com.msa2024.hello2.board.BoardMapper;
import com.msa2024.hello2.board.BoardTokenMapper;
import com.msa2024.hello2.entity.BoardFileVO;
import com.msa2024.hello2.entity.BoardImageFileVO;
import com.msa2024.hello2.entity.BoardTokenVO;
import com.msa2024.hello2.entity.BoardVO;
import com.msa2024.hello2.page.PageRequestVO;
import com.msa2024.hello2.page.PageResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private static final long serialVersionUID = 1L;

	private final String CURR_IMAGE_REPO_PATH = "c:\\upload";
	private final BoardMapper boardMapper;
	private final BoardFileMapper boardFileMapper;
	private final BoardTokenMapper boardTokenMapper;
	private final BoardImageFileMapper boardImageFileMapper;

	//날자 서식을 생성한다
	private final SimpleDateFormat date_format = new SimpleDateFormat(File.separator + "YYYY" + File.separator + "MM" + File.separator + "dd");

	public PageResponseVO<BoardVO> getList(PageRequestVO pageRequestVO) {
		List<BoardVO> list = boardMapper.getList(pageRequestVO);
		int total = boardMapper.getTotalCount(pageRequestVO);

		log.info("list {} ", list);
		log.info("total  = {} ", total);

		PageResponseVO<BoardVO> pageResponseVO = PageResponseVO.<BoardVO>withAll()
				.list(list)
				.total(total)
				.size(pageRequestVO.getSize())
				.pageNo(pageRequestVO.getPageNo())
				.build();

		return pageResponseVO;
	}

	public BoardVO view(BoardVO board)  {
		//view Count의 값을 증가한다.
		//만약 값을 증가 하지 못하면 게시물이 존재하지 않는 경우임
		if (0 == boardMapper.incViewCount(board)) {
			return null;
		}
		//view Count의 값이 증가된 객체를 얻는다
		BoardVO resultVO = boardMapper.view(board);
		log.info(resultVO.getView_count());
		log.info(resultVO.toString());

		//첨부파일을 얻는다
		resultVO.setBoardFileVO(boardFileMapper.getBoardFileVO(board));

		return resultVO;
	}

	public int delete(BoardVO board)  {
		return boardMapper.delete(board);
	}


	public BoardVO updateForm(BoardVO board)  {
		return boardMapper.view(board);
	}

	public int update(BoardVO board) {
		return boardMapper.update(board);
	}

	public int insert(BoardVO board)  {
		//게시물 등록시 게시물의 번호를 얻는다
		int result = boardMapper.insert(board);

		//MultipartFile 객체를 파일에 저장한다
		BoardFileVO boardFileVO = writeFile(board.getFile());
		if (boardFileVO != null) {
			//첨부파일에 게시물의 아이디를 설정한다
			boardFileVO.setBno(board.getBno());

			//저장에 파일 정보를 DB에 저장한다
			result = boardFileMapper.insert(boardFileVO);
		}
//board_token의 상태를 임시 상태에서 완료 상태로 변경한다
		boardTokenMapper.updateStatusComplate(board.getBoard_token());

		//게시물 이미지의 board_token 값인 자료를 bno로 변경한다
		boardImageFileMapper.updateBoardNo(board);

		return result;
	}

	//MultipartFile 객체를 파일에 저장한다
	private BoardFileVO writeFile(MultipartFile file) {
		if (file == null || file.getName() == null) return null;

		Calendar now = Calendar.getInstance();
		//저장위치를 오늘의 날짜로 한다
		String realFolder = date_format.format(now.getTime());
		//실제 저장 위치를 생성한다
		File realPath = new File(CURR_IMAGE_REPO_PATH + realFolder);
		//오늘 날짜에 대한 폴더가 없으면 생성한다
		if(!realPath.exists()) {
			realPath.mkdirs();
		}
		//실제 파일명으로 사용할 이름을 생성한다
		String fileNameReal = UUID.randomUUID().toString();
		File realFile = new File(realPath, fileNameReal);

		//파일을 실제 위치에 저장한다
		try {
			file.transferTo(realFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("transferTo : ", e);
			return null;
		}

		//저장된 첨부파일 객체를 리턴한다
		return BoardFileVO.builder()
				.content_type(file.getContentType())
				.original_filename(file.getOriginalFilename())
				.real_filename(realFile.getAbsolutePath())
				.size(file.getSize())
				.build();
	}

	public BoardFileVO getBoardFile(int board_file_no) {
		return boardFileMapper.getBoardFile(board_file_no);
	}


	public String getBoardToken() {
		final String board_token = UUID.randomUUID().toString();

		boardTokenMapper.insert(board_token);

		return board_token;
	}


	public String boardImageFileUpload(String board_token, MultipartFile file) {
		Calendar now = Calendar.getInstance();
		//저장위치를 오늘의 날짜로 한다
		String realFolder = date_format.format(now.getTime());
		//실제 저장 위치를 생성한다
		File realPath = new File(CURR_IMAGE_REPO_PATH + realFolder);
		//오늘 날짜에 대한 폴더가 없으면 생성한다
		if(!realPath.exists()) {
			realPath.mkdirs();
		}
		//실제 파일명으로 사용할 이름을 생성한다
		String fileNameReal = UUID.randomUUID().toString();
		File realFile = new File(realPath, fileNameReal);

		//파일을 실제 위치에 저장한다
		try {
			file.transferTo(realFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("transferTo : ", e);
			return null;
		}

		//게시물에 내용에 추가되는 이미지 파일 객체를 생성한다
		BoardImageFileVO boardImageFileVO = BoardImageFileVO.builder()
				.board_token(board_token)
				.content_type(file.getContentType())
				.original_filename(file.getOriginalFilename())
				.real_filename(realFile.getAbsolutePath())
				.size(file.getSize())
				.build();

		//게시물에 내용에 추가되는 이미지 파일을 DB에 저장한다
		boardImageFileMapper.insert(boardImageFileVO);

		return boardImageFileVO.getBoard_image_file_id();
	}

	public BoardImageFileVO getBoardImageFile(String board_image_file_id) {
		return boardImageFileMapper.findById(board_image_file_id);
	}


}







