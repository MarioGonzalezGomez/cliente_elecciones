package com.mggcode.cliente_elecciones.utils;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.DTO.CircunscripcionDTO;
import com.mggcode.cliente_elecciones.DTO.CpDTO;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarmenDtoReader {

    private static CarmenDtoReader instance = null;

    private CarmenDtoReader() {

    }

    public static CarmenDtoReader getInstance() {
        if (instance == null)
            instance = new CarmenDtoReader();
        return instance;
    }

    public CarmenDTO readCarmenDto(int tipoElecciones) {
        CarmenDTO res = readCsv(tipoElecciones);
        if (res == null)
            res = readExcel(tipoElecciones);
        return res;
    }

    public CarmenDTO readExcel(int tipoElecciones) {
        try {
            FileInputStream fileInputStream;
            if (tipoElecciones == 1 || tipoElecciones == 2) {
                fileInputStream = new FileInputStream("C:\\Elecciones2023\\DATOS\\C_MapaMayorias.xlsx");
            } else {
                fileInputStream = new FileInputStream("C:\\Elecciones2023\\DATOS\\C_MapaMayoriasSondeo.xlsx");
            }

            Workbook workbook = new XSSFWorkbook(fileInputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            data.get(i).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            data.get(i).add(cell.getNumericCellValue() + "");
                            break;
                        default:
                            data.get(i).add(" ");
                    }
                }
                i++;
            }


            List<String> read = data.get(1);
            CircunscripcionDTO circunscripcion = new CircunscripcionDTO(
                    read.get(0), read.get(1), read.get(2),
                    read.get(3), read.get(4), Double.parseDouble(read.get(5)),
                    (int) Double.parseDouble(read.get(6)),
                    Double.parseDouble(read.get(7)), Double.parseDouble(read.get(8)),
                    Double.parseDouble(read.get(9)), read.get(10),
                    (int) Double.parseDouble(read.get(11)), (int) Double.parseDouble(read.get(12)),
                    read.get(13), Double.parseDouble(read.get(14)),
                    Double.parseDouble(read.get(15)), Double.parseDouble(read.get(16))
            );

            int nPartidos = ((int) Double.parseDouble(read.get((17))));
            List<CpDTO> listaCp = new ArrayList<>();
            for (int j = 1; j <= nPartidos; j++) {
                read = data.get(2 + j);
                CpDTO cpDTO = new CpDTO(read.get(0), read.get(1),
                        (int) Double.parseDouble(read.get(2)), (int) Double.parseDouble(read.get(3)),
                        (int) Double.parseDouble(read.get(4)), Double.parseDouble(read.get(5)),
                        Double.parseDouble(read.get(6)), (int) Double.parseDouble(read.get(7)),
                        read.get(8), read.get(9), (int) Double.parseDouble(read.get(10)), (int) Double.parseDouble(read.get(11)), Double.parseDouble(read.get(12)));
                listaCp.add(cpDTO);
            }
            CarmenDTO carmenDTO = new CarmenDTO(circunscripcion, nPartidos, listaCp);
            var a = CircunscripcionPartido.mapFromCpDTO(circunscripcion, listaCp.get(0));
            System.out.println(a);
            return carmenDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CarmenDTO readCsv(int tipoElecciones) {
        try {
            // File test = new File("C:\\Elecciones2023\\DATOS\\F_" + codigo + ".csv");
            // Path path = Path.of("C:\\Elecciones2023\\DATOS\\F_" + codigo + ".csv");
            Path path;
            if (tipoElecciones == 1 || tipoElecciones == 2) {
                path = Path.of("C:\\Elecciones2023\\DATOS\\C_MapaMayorias.csv");
            } else {
                path = Path.of("C:\\Elecciones2023\\DATOS\\C_MapaMayoriasSondeo.csv");
            }

            List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
            String lineCircunscripcion = lines.stream().skip(1).toList().get(0);
            var split = lineCircunscripcion.split(";");
            CircunscripcionDTO circunscripcion = new CircunscripcionDTO(
                    split[0],
                    split[1],
                    split[2],
                    split[3],
                    split[4],
                    Double.parseDouble(split[5]),
                    Integer.parseInt(split[6]),
                    Double.parseDouble(split[7]),
                    Double.parseDouble(split[8]),
                    Double.parseDouble(split[9]),
                    split[10],
                    Integer.parseInt(split[11]),
                    Integer.parseInt(split[12]),
                    split[13],
                    Double.parseDouble(split[14]),
                    Double.parseDouble(split[15]),
                    Double.parseDouble(split[16])
            );
            System.out.println(lines.stream().skip(3).toList());
            var cp = lines.stream().skip(3).map(this::cpDTOBuilder).toList();
            System.out.println(cp);
            return new CarmenDTO(circunscripcion, cp.size(), cp);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private CpDTO cpDTOBuilder(String line) {
        var split = line.split(";");
        return new CpDTO(split[0], split[1],
                (int) Double.parseDouble(split[2]), (int) Double.parseDouble(split[3]),
                (int) Double.parseDouble(split[4]), Double.parseDouble(split[5]),
                Double.parseDouble(split[6]), (int) Double.parseDouble(split[7]),
                split[8], split[9], (int) Double.parseDouble(split[10]), (int) Double.parseDouble(split[11]), Double.parseDouble(split[12]));
    }
}
