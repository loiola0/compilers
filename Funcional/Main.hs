import Data.List (delete, minimumBy, nub)
import Data.Ord (comparing)
import System.CPUTime
import System.IO (writeFile)
import Text.Printf (printf)

type Cidade = (Double, Double)

listaCidades :: [Cidade]
listaCidades =
  [ (94, 68),
    (12, 15),
    (20, 26),
    (96, 44),
    (71, 95),
    (65, 63),
    (21, 42),
    (58, 13),
    (48, 19),
    (5, 11)
  ]

distancia :: Cidade -> Cidade -> Double
distancia (x1, y1) (x2, y2) = sqrt (x * x + y * y)
  where
    x = x1 - x2
    y = y1 - y2

distanciaTotal :: [Cidade] -> Double
distanciaTotal [] = 0
distanciaTotal [_] = 0
distanciaTotal (c1 : c2 : resto) = distancia c1 c2 + distanciaTotal (c2 : resto)

distanciaComVolta :: [Cidade] -> Double
distanciaComVolta cidades = distanciaTotal cidades + distancia (head cidades) (last cidades)

maisProxima :: Cidade -> [Cidade] -> Cidade
maisProxima cidade = minimumBy (comparing (distancia cidade))

vizinhoMaisProximo :: [Cidade] -> [Cidade]
vizinhoMaisProximo [] = []
vizinhoMaisProximo (inicial : resto) = vizinhoMaisProximoAux [inicial] resto

vizinhoMaisProximoAux :: [Cidade] -> [Cidade] -> [Cidade]
vizinhoMaisProximoAux caminho [] = caminho
vizinhoMaisProximoAux caminho naoVisitadas =
  let ultimaCidade = last caminho
      proxima = maisProxima ultimaCidade naoVisitadas
   in vizinhoMaisProximoAux (caminho ++ [proxima]) (delete proxima naoVisitadas)

imprimeCaminho :: [Cidade] -> IO ()
imprimeCaminho cidades = do
  let caminho = vizinhoMaisProximo cidades
  let distTotal = distanciaComVolta caminho
  putStrLn $ "Melhor caminho encontrado: " ++ show caminho
  printf "Distância total: %.2f km\n" distTotal

medirTempo :: IO a -> IO a
medirTempo action = do
  start <- getCPUTime
  result <- action
  end <- getCPUTime
  let diff = fromIntegral (end - start) / (10 ^ 12)
  printf "Tempo de execução: %.3f segundos\n" (diff :: Double)
  return result

salvarCaminho :: [Cidade] -> IO ()
salvarCaminho cidades = do
  let caminho = vizinhoMaisProximo cidades
  let conteudo = concatMap (\(x, y) -> show x ++ "," ++ show y ++ "\n") caminho
  writeFile "caminho.csv" conteudo
  putStrLn "Cidades e caminho salvos no arquivo caminho.csv."

main :: IO ()
main = medirTempo $ do
  print listaCidades
  imprimeCaminho listaCidades
  salvarCaminho listaCidades
