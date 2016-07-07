package ch.slrg.mvp.service;

import ch.slrg.mvp.domain.Member;
import ch.slrg.mvp.repository.MemberRepository;
import ch.slrg.mvp.web.rest.dto.MemberDTO;
import ch.slrg.mvp.web.rest.mapper.MemberMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Member.
 */
@Service
@Transactional
public class MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private MemberMapper memberMapper;

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save
     * @return the persisted entity
     */
    public MemberDTO save(MemberDTO memberDTO) {
        log.debug("Request to save Member : {}", memberDTO);
        Member member = memberMapper.memberDTOToMember(memberDTO);
        member = memberRepository.save(member);
        MemberDTO result = memberMapper.memberToMemberDTO(member);
        return result;
    }

    /**
     *  Get all the members.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Member> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        Page<Member> result = memberRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one member by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MemberDTO findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        Member member = memberRepository.findOne(id);
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(member);
        return memberDTO;
    }

    /**
     *  Delete the  member by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.delete(id);
    }

    public List<Member> search(boolean aquateam, boolean skipper, boolean boatdriver) {
        log.debug("Look for Members with elements");
        return memberRepository.findMembersByAquateamOrSkipperOrBoatdriver(aquateam, skipper, boatdriver);
    }

    public void export(HttpServletResponse response, boolean aquateam, boolean skipper, boolean boatdriver){
        log.debug("Downloading Excel report");

        // 1. Create new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2. Create new worksheet
        HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

        // 3. Define starting indices for rows and columns
        int startRowIndex = 0;
        int startColIndex = 0;

        Font font = worksheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Create cell style for the headers
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        // Create the column headers
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex +2);
        rowHeader.setHeight((short) 500);

        HSSFCell cell1H = rowHeader.createCell(startColIndex+0);
        cell1H.setCellValue("Id");
        cell1H.setCellStyle(headerCellStyle);

        HSSFCell cell2H = rowHeader.createCell(startColIndex+1);
        cell2H.setCellValue("Name");
        cell2H.setCellStyle(headerCellStyle);

        HSSFCell cell3H = rowHeader.createCell(startColIndex+2);
        cell3H.setCellValue("Lastname");
        cell3H.setCellStyle(headerCellStyle);

        HSSFCell cell4H = rowHeader.createCell(startColIndex+3);
        cell4H.setCellValue("email");
        cell4H.setCellStyle(headerCellStyle);

        HSSFCell cell5H = rowHeader.createCell(startColIndex+4);
        cell5H.setCellValue("aqua");
        cell5H.setCellStyle(headerCellStyle);

        HSSFCell cell6H = rowHeader.createCell(startColIndex+5);
        cell6H.setCellValue("skipper");
        cell6H.setCellStyle(headerCellStyle);

        HSSFCell cell7H = rowHeader.createCell(startColIndex+6);
        cell7H.setCellValue("boatdriver");
        cell7H.setCellStyle(headerCellStyle);

        startRowIndex += 2;

        List<Member> datasource = search(aquateam, skipper, boatdriver);

        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(true);


        for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {
            // Create a new row
            HSSFRow row = worksheet.createRow((short) i+1);

            // Retrieve the id value
            HSSFCell cell1 = row.createCell(startColIndex+0);
            cell1.setCellValue(datasource.get(i-2).getId());
            cell1.setCellStyle(bodyCellStyle);

            // Retrieve the brand value
            HSSFCell cell2 = row.createCell(startColIndex+1);
            cell2.setCellValue(datasource.get(i-2).getName());
            cell2.setCellStyle(bodyCellStyle);

            // Retrieve the model value
            HSSFCell cell3 = row.createCell(startColIndex+2);
            cell3.setCellValue(datasource.get(i-2).getLastname());
            cell3.setCellStyle(bodyCellStyle);

            // Retrieve the maximum power value
            HSSFCell cell4 = row.createCell(startColIndex+3);
            cell4.setCellValue(datasource.get(i-2).getEmail());
            cell4.setCellStyle(bodyCellStyle);

            // Retrieve the price value
            HSSFCell cell5 = row.createCell(startColIndex+4);
            cell5.setCellValue(datasource.get(i-2).isAquateam());
            cell5.setCellStyle(bodyCellStyle);

            // Retrieve the efficiency value
            HSSFCell cell6 = row.createCell(startColIndex+5);
            cell6.setCellValue(datasource.get(i-2).isSkipper());
            cell6.setCellStyle(bodyCellStyle);

            HSSFCell cell7 = row.createCell(startColIndex+6);
            cell7.setCellValue(datasource.get(i-2).isBoatdriver());
            cell7.setCellStyle(bodyCellStyle);
        }

        // 6. Set the response properties
        String fileName = "export.xls";
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        // Make sure to set the correct content type
        response.setContentType("application/vnd.ms-excel");

        //7. Write to the output stream
        log.debug("Writing report to the stream");
        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            worksheet.getWorkbook().write(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            log.error("Unable to write report to the output stream");
        }

    }

}
