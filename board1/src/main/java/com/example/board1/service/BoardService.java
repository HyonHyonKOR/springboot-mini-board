package com.example.board1.service;

import com.example.board1.entity.Board;
import com.example.board1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //글 작성
    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }


    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword,pageable);
    }


    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}

