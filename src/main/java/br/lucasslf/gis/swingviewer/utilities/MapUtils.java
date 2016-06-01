package br.lucasslf.gis.swingviewer.utilities;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Classe MapUtils
 */
public class MapUtils {

    /**
     *
     * Retorna o path para o arquivo de Tile Cacheado
     *
     * @param level Nível do tile
     * @param col Coluna do tile
     * @param row Linha do tile
     * @param baseMapServiceID Id do serviço de mapa requisitado
     * @return Caminho para o arquivo
     */
    public static String resolveTileFileName(int level, int col, int row, String baseMapServiceID) {
        return "D:/appfiles/temp" + baseMapServiceID + "-" + level + "-" + col + "-" + row;
    }

    /**
     * Calcula a quantidade real de metros por pixel dada uma escala e uma
     * resolução de tela em Pixels/Centímetro
     *
     * @param scale Escala para o cálculo
     * @param pixelsPerCM Resolução da tela em pixels por centímetro
     * @return Metros por Pixel
     */
    public static double getMetersPerPixel(double scale,int dpi) {
        return 1 / scale / 100 / getPixelsPerCm(dpi);
    }

    /**
     * Extrai o milhar de um double Exemplo: 23454 -> 20000 1456 -> 1000 5000 ->
     * 5000 5667 -> 5000 200 -> 200
     *
     * @param thousand Número para ser extraído
     * @return Número inteiro milhar
     */
    public static int getThousandRound(double thousand) {
        if (thousand < 1000) {
            return (int) thousand;
        }
        return (int) (thousand - (((thousand / 1000) % 1) * 1000));
    }

    /**
     * Calcula o ponto equivalente no mapa para um ponto na janela
     *
     * @param componentPoint Ponto na janela
     * @param scale Escala
     * @param pixelsPerCM Resolução da imagem na tela em Pixels/Cm
     * @param height Tamanho vertical da tela
     * @return Ponto equivalente no mapa
     */
    public static Point2D getMapPointFromComponentPoint(Point2D componentPoint, Double scale, int dpi, double height) {
        double metersPerPixel = MapUtils.getMetersPerPixel(scale, dpi);
        return new Point2D.Double(componentPoint.getX() * metersPerPixel, height - (componentPoint.getY() * metersPerPixel));
    }

    /**
     * Calcula um novo corner inferior esquerdo para um zoom efetuado em um
     * ponto específico
     *
     * @param originalPosition Posição original do corner inferior esquerdo
     * @param zoomPosition Ponto onde foi efetuado o zoom
     * @param newScale Nova escala definida pelo zoom (quantidade de zoom)
     * @param pixelsPerCM Quantidade de pixels por centímetros (resolução)
     * @param height Tamanho do mapa na tela
     * @return Ponto representando o novo corner inferior esquerdo do mapa após
     * o zoom
     */
    public static Point2D calculateNewCornerForZoom(Point2D originalPosition, Point2D zoomPosition, Double newScale, int dpi, Double height) {
        double newX = originalPosition.getX() - (getMetersPerPixel(newScale, dpi) * zoomPosition.getX());
        double newY = originalPosition.getY() - (getMetersPerPixel(newScale, dpi) * (height - zoomPosition.getY()));
        return new Point2D.Double(newX, newY);
    }

    /**
     * Calcula um novo corner inferior esquerdo para um pan efetuado no mapa
     *
     * @param translationX Quantidade de translação horizontal
     * @param translationY Quantidade de translação vertical
     * @param scale Escala do mapa
     * @param pixelsPerCM Quantidade de pixels por centímetros (resolução)
     * @param leftInferiorCorner Posição original do corner inferior esquerdo
     * @return Ponto representando o novo corner inferior esquerdo do mapa após
     * o pan
     */
    public static Point2D calculateNewCornerForPAN(double translationX, double translationY, Double scale, int dpi, Point2D leftInferiorCorner) {
        double metersPerPixel = MapUtils.getMetersPerPixel(scale, dpi);
        double deltaX = metersPerPixel * translationX;
        double deltaY = metersPerPixel * translationY;
        return new Point2D.Double(leftInferiorCorner.getX() - deltaX, leftInferiorCorner.getY() - deltaY);
    }

    public static String getTextScale(double scale) {
        double s = 1.0 / scale;
        return String.format("1 : %,.2f", s);
    }

    public static String getFormatedDistance(double distance) {
        if (distance > 1000) {
            return String.format("%,.2f Km", distance / 1000);
        } else {
            return String.format("%.0f m", distance);
        }
    }

    public static String getFormattedCoordinates(Point2D position) {
        return String.format("N: %,.2f m E: %,.2f m ", position.getY(), position.getX());
    }
    
    
    public static Color getColorFromRGBAArray(int[]rgba){
        if(rgba == null)
            return Color.WHITE;
        else
            return new Color(rgba[0],rgba[1],rgba[2],rgba[3]);
    }
    
    
    public static final double getPixelsPerCm(int dpi) {
        return dpi / 2.54;
    }
}
