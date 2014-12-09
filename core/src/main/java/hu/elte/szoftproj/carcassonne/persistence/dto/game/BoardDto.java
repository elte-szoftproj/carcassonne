package hu.elte.szoftproj.carcassonne.persistence.dto.game;

import java.util.List;

public class BoardDto {

    List<BoardItemDto> items;

    public BoardDto() {

    }

    public BoardDto(List<BoardItemDto> items) {
        this.items = items;
    }

    public List<BoardItemDto> getItems() {
        return items;
    }
}
