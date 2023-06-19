package pl.aml.bk.imdgpoc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;
import pl.aml.bk.imdgpoc.service.InfoService;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/list")
    public ResponseEntity<List<InfoDto>> getInfoList() {
        List<InfoDto> infoList = infoService.getInfoList();
        return ResponseEntity.ok(infoList);
    }

    @GetMapping("/get")
    public ResponseEntity<InfoDto> getInfo(@RequestParam UUID id) {
        InfoDto infoDto = infoService.getInfo(id);

        return ResponseEntity.ok(infoDto);
    }

    @PostMapping("/add")
    public ResponseEntity<InfoDto> addInfo(@RequestBody InfoDto infoDto) {
        InfoDto result = infoService.addNewInfo(infoDto.info());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update")
    public ResponseEntity<InfoDto> updateInfo(@RequestBody InfoDto infoDto) {
        InfoDto result = infoService.updateInfo(infoDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam UUID id) {
        boolean delete = infoService.delete(id);
        if (delete) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<BigInteger> count() {
        return ResponseEntity.ok(infoService.count());
    }

}
