SELECT codigo,
       titulo,
       tipo,
       CASE WHEN disponivel THEN 'Disponível' ELSE 'Emprestado' END AS situacao
FROM item
ORDER BY tipo, titulo;

SELECT u.nome  AS usuario,
       i.titulo,
       e.data_retirada,
       e.data_devolucao_prevista
FROM emprestimo e
JOIN usuario u ON u.id = e.usuario_id
JOIN item    i ON i.id = e.item_id
WHERE e.data_devolucao IS NULL
ORDER BY e.data_devolucao_prevista;

SELECT u.nome,
       COALESCE(SUM(e.valor_multa), 0) AS total_multas
FROM usuario u
LEFT JOIN emprestimo e ON e.usuario_id = u.id
GROUP BY u.id, u.nome
ORDER BY total_multas DESC;